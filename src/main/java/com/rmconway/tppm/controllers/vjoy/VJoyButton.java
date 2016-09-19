package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.inputs.Button;

/**
 * A VJoyButton is a button on a vJoy device.
 */
public class VJoyButton implements Button {
    private String name;
    private int vjoyDeviceID;
    private int vjoyButtonID;

    public VJoyButton(String name, int vjoyDeviceID, int vjoyButtonID) {
        this.name = name;
        this.vjoyDeviceID = vjoyDeviceID;
        this.vjoyButtonID = vjoyButtonID;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(double value) {
        // Call vJoy FFI
    }
}
