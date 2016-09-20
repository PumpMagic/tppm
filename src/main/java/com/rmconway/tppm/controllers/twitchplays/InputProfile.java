package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.Input;

import java.util.Map;

/**
 * InputProfile captures a bunch of parameters relating to game-specific Twitch Plays controller behavior.
 * For example, the game's framerate and its ideal implicit input -> input delays.
 */
public class InputProfile {
    public int framesPerSecond;

    // Default delays
    public long movementOptionToSameMovementOptionDelay;
    public long movementOptionToOtherMovementOptionDelay;
    public long movementOptionToNonMovementOptionDelay;

    public long nonMovementOptionToSameNonMovementOptionDelay;
    public long nonMovementOptionToOtherNonMovementOptionDelay;
    public long nonMovementOptionToMovementOptionDelay;

    public Map<Input, InputPurpose> inputPurposes;
    public Map<Input, Long> defaultInputDurations;

    public long nanosecondsPerFrame() {
        return 1000000000 / this.framesPerSecond;
    }

    public long suggestedDelay(Input first, Input second) {
        switch (this.inputPurposes.get(first)) {
            case MOVEMENT:
                switch (this.inputPurposes.get(second)) {
                    case MOVEMENT:
                        if (first == second) {
                            return this.movementOptionToSameMovementOptionDelay;
                        } else {
                            return this.movementOptionToOtherMovementOptionDelay;
                        }
                    case NON_MOVEMENT:
                        return this.movementOptionToNonMovementOptionDelay;
                }
                break;
            case NON_MOVEMENT:
                switch (this.inputPurposes.get(second)) {
                    case MOVEMENT:
                        return this.nonMovementOptionToMovementOptionDelay;
                    case NON_MOVEMENT:
                        if (first == second) {
                            return this.nonMovementOptionToSameNonMovementOptionDelay;
                        } else {
                            return nonMovementOptionToOtherNonMovementOptionDelay;
                        }
                }
        }

        return 0;
    }

    public InputProfile(int framesPerSecond, long movementOptionToSameMovementOptionDelay, long movementOptionToOtherMovementOptionDelay, long movementOptionToNonMovementOptionDelay, long nonMovementOptionToSameNonMovementOptionDelay, long nonMovementOptionToOtherNonMovementOptionDelay, long nonMovementOptionToMovementOptionDelay, Map<Input, InputPurpose> inputPurposes, Map<Input, Long> defaultInputDurations) {
        this.framesPerSecond = framesPerSecond;
        this.movementOptionToSameMovementOptionDelay = movementOptionToSameMovementOptionDelay;
        this.movementOptionToOtherMovementOptionDelay = movementOptionToOtherMovementOptionDelay;
        this.movementOptionToNonMovementOptionDelay = movementOptionToNonMovementOptionDelay;
        this.nonMovementOptionToSameNonMovementOptionDelay = nonMovementOptionToSameNonMovementOptionDelay;
        this.nonMovementOptionToOtherNonMovementOptionDelay = nonMovementOptionToOtherNonMovementOptionDelay;
        this.nonMovementOptionToMovementOptionDelay = nonMovementOptionToMovementOptionDelay;
        this.inputPurposes = inputPurposes;
        this.defaultInputDurations = defaultInputDurations;
    }
}
