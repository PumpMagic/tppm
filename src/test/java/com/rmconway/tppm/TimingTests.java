package com.rmconway.tppm;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Compare the accuracy of different sleep techniques
 */
public class TimingTests {
    private static final int GOAL_WAIT = 33333333; // 33.3... ms in ns
    private static final int TEST_CYCLES = 1024;
    private static final int NUM_THREADS = 4;

    private static long runSleepingTest(int targetNs) {
        long targetMillis = (targetNs - (targetNs % 1000000)) / 1000000; // ms portion of goal wait
        int targetNanos = targetNs % 1000000; // ns portion of goal wait

        long timeBeforeSleep = System.nanoTime();
        try {
            Thread.sleep(targetMillis, targetNanos);
        } catch (InterruptedException e) {
            System.out.println("ERROR: Thread interrupted");
        }
        long timeAfterSleep = System.nanoTime();

        long absoluteError = Math.abs((timeAfterSleep - timeBeforeSleep) - targetNs);

        return absoluteError;
    }

    private static long runBlockingTest(int targetNs) {
        long timeBeforeSleep = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            if ((currentTime - timeBeforeSleep) > targetNs) {
                break;
            }
        }
        long timeAfterSleep = System.nanoTime();

        long absoluteError = Math.abs((timeAfterSleep - timeBeforeSleep) - targetNs);

        return absoluteError;
    }

    private static class BlockTestsThread implements Runnable {
        private int targetNs;
        private int numIterations;
        private List<Long> absoluteErrors;

        public BlockTestsThread(int targetNs, int numIterations, List<Long> absoluteErrors) {
            this.targetNs = targetNs;
            this.numIterations = numIterations;
            this.absoluteErrors = absoluteErrors;
        }

        public void run() {
            IntStream.range(0, this.numIterations).forEach(i -> {
                this.absoluteErrors.add(runBlockingTest(targetNs));
            });
        }
    }

    @Test
    public void testSleepingAccuracy() throws Exception {
        DescriptiveStatistics absoluteErrorStats = new DescriptiveStatistics();

        IntStream.range(0, TEST_CYCLES).forEach(i -> {
            absoluteErrorStats.addValue(runSleepingTest(GOAL_WAIT));
        });

        System.out.println("Sleeping error mean: " + String.valueOf(absoluteErrorStats.getMean()));
    }

    @Test
    public void testSingleThreadedBlockingAccuracy() {
        DescriptiveStatistics absoluteErrorStats = new DescriptiveStatistics();

        IntStream.range(0, TEST_CYCLES).forEach(i -> {
            absoluteErrorStats.addValue(runBlockingTest(GOAL_WAIT));
        });

        System.out.println("Single thread blocking error mean: " + String.valueOf(absoluteErrorStats.getMean()));
    }

    @Test
    public void testMultiThreadedBlockingAccuracy() {
        DescriptiveStatistics absoluteErrorStats = new DescriptiveStatistics();

        List<Long> absoluteErrorsBlockingThreads = Collections.synchronizedList(new ArrayList<Long>(TEST_CYCLES));

        ArrayList<Thread> threads = new ArrayList<>(NUM_THREADS);

        // Create the threads
        IntStream.range(0, NUM_THREADS).forEach(i -> {
            Thread t = new Thread(new BlockTestsThread(GOAL_WAIT, TEST_CYCLES / NUM_THREADS, absoluteErrorsBlockingThreads));
            threads.add(t);
        });

        // Run all of the threads
        for (Thread thread : threads) {
            thread.run();
        }

        // Wait for all of the threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                fail("Unable to join all threads");
            }
        }

        // Convert the results into a Commons Math-friendly format
        for (Long error : absoluteErrorsBlockingThreads) {
            absoluteErrorStats.addValue(error);
        }

        System.out.println("Multi-threaded blocking error mean: " + String.valueOf(absoluteErrorStats.getMean()));
    }

}