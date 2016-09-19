package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.Input;
import com.rmconway.tppm.controllers.models.SNESController;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;

/**
 * Created by owner on 9/19/16.
 */
public class TwitchPlaysParserTests {
    @Test
    public void testParser() throws Exception {
        int DEFAULT_INPUT_DURATION_FRAMES =  15;
        long NANOSECONDS_PER_FRAME = 33333333;

        SNESController controller = SNESController.build(0).get();
        Map<Input, Long> defaultDurations = new HashMap<>(controller.getInputs().size());
        for (Input i : controller.getInputs()) {
            defaultDurations.put(i, DEFAULT_INPUT_DURATION_FRAMES*NANOSECONDS_PER_FRAME);
        }
        TwitchPlaysParser parser = new TwitchPlaysParser(controller, NANOSECONDS_PER_FRAME, defaultDurations);

        Optional<PriorityQueue<TimedInputCommand>> command;
        command = parser.parse("a");
        command = parser.parse("a 5s");
        command = parser.parse("a 5ms");
        command = parser.parse("a 1f");
        command = parser.parse("a 4s (9s) a 3f a");
    }

}