package com.rmconway.tppm.controllers.twitchplays.games;

import com.rmconway.tppm.controllers.inputs.Input;
import com.rmconway.tppm.controllers.models.SNESController;
import com.rmconway.tppm.controllers.twitchplays.InputProfile;
import com.rmconway.tppm.controllers.twitchplays.InputPurpose;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by owner on 9/19/16.
 */
public class InputProfiles {
    public static InputProfile SuperMarioRPG(SNESController controller) {
        int framesPerSecond = 60;

        long movementOptionToSameMovementOptionDelay = 66666666;
        long movementOptionToOtherMovementOptionDelay = 0;
        long movementOptionToNonMovementOptionDelay = -33333333;

        long nonMovementOptionToSameNonMovementOptionDelay = 66666666;
        long nonMovementOptionToOtherNonMovementOptionDelay = 0;
        long nonMovementOptionToMovementOptionDelay = 0;

        Set<? extends Input> inputs = controller.getInputs();

        Map<Input, InputPurpose> inputPurposes = new HashMap<>(inputs.size());
        inputPurposes.put(controller.getInputByName("up").get(), InputPurpose.MOVEMENT);
        inputPurposes.put(controller.getInputByName("down").get(), InputPurpose.MOVEMENT);
        inputPurposes.put(controller.getInputByName("left").get(), InputPurpose.MOVEMENT);
        inputPurposes.put(controller.getInputByName("right").get(), InputPurpose.MOVEMENT);
        inputPurposes.put(controller.getInputByName("a").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("b").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("x").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("y").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("l").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("r").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("select").get(), InputPurpose.NON_MOVEMENT);
        inputPurposes.put(controller.getInputByName("start").get(), InputPurpose.NON_MOVEMENT);

        Map<Input, Long> defaultInputDurations = new HashMap<>(inputs.size());
        defaultInputDurations.put(controller.getInputByName("up").get(), 500000000L);
        defaultInputDurations.put(controller.getInputByName("down").get(), 500000000L);
        defaultInputDurations.put(controller.getInputByName("left").get(), 500000000L);
        defaultInputDurations.put(controller.getInputByName("right").get(), 500000000L);
        defaultInputDurations.put(controller.getInputByName("a").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("b").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("x").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("y").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("l").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("r").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("select").get(), 250000000L);
        defaultInputDurations.put(controller.getInputByName("start").get(), 250000000L);

        return new InputProfile(framesPerSecond,
                movementOptionToSameMovementOptionDelay,
                movementOptionToOtherMovementOptionDelay,
                movementOptionToNonMovementOptionDelay,
                nonMovementOptionToSameNonMovementOptionDelay,
                nonMovementOptionToOtherNonMovementOptionDelay,
                nonMovementOptionToMovementOptionDelay,
                inputPurposes,
                defaultInputDurations);
    }
}
