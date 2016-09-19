package com.rmconway.tppm.controllers.models;

import com.rmconway.tppm.controllers.inputs.HasInputs;
import com.rmconway.tppm.controllers.inputs.Input;
import com.rmconway.tppm.controllers.vjoy.VJoyButton;
import com.rmconway.tppm.controllers.vjoy.VJoyDevice;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A representation of a Super NES controller, implemented as a vJoy device.
 */
public class SNESController implements VJoyDevice, HasInputs {
    private int vjoyDeviceID;
    private Set<VJoyButton> buttons;

    public static Optional<SNESController> build(int vjoyDeviceID) {
        // Try claiming the device through vJoy
        // On success, create the buttons and call the private constructor; return it
        // On failure, return empty

        Set<VJoyButton> buttons = new HashSet<>();

        buttons.add(new VJoyButton("a", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("b", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("x", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("y", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("up", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("down", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("left", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("right", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("l", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("r", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("select", vjoyDeviceID, 0));
        buttons.add(new VJoyButton("start", vjoyDeviceID, 0));

        return Optional.of(new SNESController(vjoyDeviceID, buttons));

        //return Optional.empty();
    }

    private SNESController(int vjoyDeviceID, Set<VJoyButton> buttons) {
        this.vjoyDeviceID = vjoyDeviceID;
        this.buttons = buttons;
    }

    public int getVJoyDeviceID() {
        return this.vjoyDeviceID;
    }

    public Set<? extends Input> getInputs() {
        return this.buttons;
    }
}
