package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.Input;
import com.rmconway.tppm.controllers.models.SNESController;
import com.rmconway.tppm.controllers.twitchplays.games.InputProfiles;
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

        SNESController snesController = SNESController.build(0).get();
        InputProfile inputProfile = InputProfiles.SuperMarioRPG(snesController);

        TwitchPlaysParser parser = new TwitchPlaysParser(snesController, inputProfile);

        Optional<PriorityQueue<TimedInputCommand>> commandChain;
        commandChain = parser.parse("a");
        commandChain = parser.parse("a 5s");
        commandChain = parser.parse("a 5ms");
        commandChain = parser.parse("a 1f");
        commandChain = parser.parse("a 4s (9s) a 3f a");
        commandChain = parser.parse("a 5s (-1f) b");
        commandChain = parser.parse("up a");
        commandChain = parser.parse("up (0s) a");
    }

}