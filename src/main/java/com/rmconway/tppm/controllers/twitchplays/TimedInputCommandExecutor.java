package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.Input;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

/**
 * A TimedInputCommandExecutor takes a priority queue of timed commands and executes it with high timing precision.
 */
public class TimedInputCommandExecutor implements Runnable {
    private Map<Input, Semaphore> inputGuards;
    private PriorityQueue<TimedInputCommand> commands;

    public TimedInputCommandExecutor(Map<Input, Semaphore> inputGuards, PriorityQueue<TimedInputCommand> commands) {
        this.inputGuards = inputGuards;
        this.commands = commands;
    }

    public void run() {
        // Get an absolute time to declare as T+0, for timing all commands
        long startTime = System.nanoTime();

        // Execute all of the commands in our chain
        while (!commands.isEmpty()) {
            TimedInputCommand command = commands.poll();
            long commandOffset = command.offset;
            Input target = command.target;
            double value = command.value;
            Semaphore guard = this.inputGuards.get(target);

            // Wait until it's time to execute the next command
            // We block rather than sleeping to achieve high precision
            // (Sleeping incurs the cost of context switching)
            long currentTime = System.nanoTime();
            while ((currentTime - startTime) <= commandOffset) {
                currentTime = System.nanoTime();
            }

            if (command.isStart) {
                // We don't have the guard - try acquiring the lock first
                if (guard.tryAcquire()) {
                    // We got the lock! Execute the command, and schedule its stopping counterpart
                    currentTime = System.nanoTime();
                    target.setValue(value);

                    TimedInputCommand resetCommand = new TimedInputCommand(target, target.defaultValue(), currentTime - startTime + command.duration, 0, false);
                    commands.add(resetCommand);
                }
            } else {
                // We have the guard, because this is a stop command and we're the ones who ran the complementing start
                // command. Execute this command and unlock
                target.setValue(value);
                guard.release();
            }
        }
    }
}
