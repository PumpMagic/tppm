package com.rmconway.tppm.controllers.models;

import com.rmconway.tppm.controllers.inputs.HasInputs;
import com.rmconway.tppm.controllers.inputs.Input;
import com.rmconway.tppm.controllers.vjoy.VJoyButton;
import com.rmconway.tppm.controllers.vjoy.VJoyDevice;
import com.rmconway.tppm.controllers.vjoy.ffi.VJoyFFI;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A representation of a Super NES controller, implemented as a vJoy device.
 */
public class SNESController implements VJoyDevice, HasInputs {
    private int vjoyDeviceID;
    private Set<VJoyButton> buttons;
    private static final int NUM_SNES_CONTROLLER_BUTTONS = 12;

    public static Optional<SNESController> build(int vjoyDeviceID) {
        // Verify the device's hardware, then try claiming it through vJoy
        // On success, create the buttons and call the private constructor; return it
        // On failure, return empty

        boolean vjoyEnabled = VJoyFFI.isVJoyEnabled();
        if (!vjoyEnabled) { return Optional.empty(); }
        boolean deviceExists = VJoyFFI.doesDeviceExist(vjoyDeviceID);
        if (!deviceExists) { return Optional.empty(); }
        int numButtons = VJoyFFI.getNumButtons(vjoyDeviceID);
        if (numButtons < NUM_SNES_CONTROLLER_BUTTONS) { return Optional.empty(); }
        boolean deviceClaimedByUs = VJoyFFI.claimDevice(vjoyDeviceID);
        if (!deviceClaimedByUs) { return Optional.empty(); }
        boolean reset = VJoyFFI.resetDevice(vjoyDeviceID);
        if (!reset) { return Optional.empty(); }

        // The device is good, and we have it!
        Set<VJoyButton> buttons = new HashSet<>();

        buttons.add(new VJoyButton("a", vjoyDeviceID, (byte) 0x00));
        buttons.add(new VJoyButton("b", vjoyDeviceID, (byte) 0x01));
        buttons.add(new VJoyButton("x", vjoyDeviceID, (byte) 0x02));
        buttons.add(new VJoyButton("y", vjoyDeviceID, (byte) 0x03));
        buttons.add(new VJoyButton("l", vjoyDeviceID, (byte) 0x04));
        buttons.add(new VJoyButton("r", vjoyDeviceID, (byte) 0x05));
        buttons.add(new VJoyButton("up", vjoyDeviceID, (byte) 0x06));
        buttons.add(new VJoyButton("down", vjoyDeviceID, (byte) 0x07));
        buttons.add(new VJoyButton("left", vjoyDeviceID, (byte) 0x08));
        buttons.add(new VJoyButton("right", vjoyDeviceID, (byte) 0x09));
        buttons.add(new VJoyButton("select", vjoyDeviceID, (byte) 0x0A));
        buttons.add(new VJoyButton("start", vjoyDeviceID, (byte) 0x0B));

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
