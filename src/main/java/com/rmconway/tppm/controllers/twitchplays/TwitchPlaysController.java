package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.HasInputs;
import com.rmconway.tppm.controllers.inputs.Input;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * A TwitchPlaysController is a text-operated device that sits in front of some video game controller and manages
 * its resources in a collaborative way (i.e., it allows only one button user at a time)
 */
public class TwitchPlaysController implements TextOperated {
    private static final long NANOSECONDS_PER_FRAME = 33333333;
    private static final int DEFAULT_INPUT_DURATION_FRAMES = 30;

    private HasInputs backingController;
    private Map<Input, Semaphore> inputGuards;
    private TwitchPlaysParser parser;

    public TwitchPlaysController(HasInputs backingController) {
        Set<? extends Input> inputs = backingController.getInputs();
        Map<Input, Semaphore> inputGuards = new HashMap<>(inputs.size());
        Map<Input, Long> defaultDurations = new HashMap<>(inputs.size());
        for (Input i : backingController.getInputs()) {
            Semaphore s = new Semaphore(1);
            inputGuards.put(i, s);

            defaultDurations.put(i, DEFAULT_INPUT_DURATION_FRAMES*NANOSECONDS_PER_FRAME);
        }

        this.backingController = backingController;
        this.inputGuards = inputGuards;

        this.parser = new TwitchPlaysParser(backingController, NANOSECONDS_PER_FRAME, defaultDurations);
    }

    public boolean handleText(String text) {
        // Parse the text
        // If it makes a valid command chain, spawn a thread with it
        // Otherwise, do nothing
        // Return whether or not the text was valid

        Optional<PriorityQueue<TimedInputCommand>> chain = this.parser.parse(text);
        if (chain.isPresent()) {
            TimedInputCommandExecutor executor = new TimedInputCommandExecutor(this.inputGuards, chain.get());
            return true;
        }

        return false;
    }
}
