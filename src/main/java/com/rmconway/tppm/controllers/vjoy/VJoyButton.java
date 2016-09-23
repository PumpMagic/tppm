package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.inputs.Button;
import com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNA;

/**
 * A VJoyButton is a button on a vJoy device.
 */
public class VJoyButton implements Button {
    private String name;
    private int vjoyDeviceID;
    private byte vjoyButtonID;

    public VJoyButton(String name, int vjoyDeviceID, byte vjoyButtonID) {
        this.name = name;
        this.vjoyDeviceID = vjoyDeviceID;
        this.vjoyButtonID = vjoyButtonID;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(double value) {
        boolean valueBoolean = value == 0.0 ? false : true;
        VJoyJNA.setButton(vjoyDeviceID, vjoyButtonID, valueBoolean);
        System.out.println(this.name + " -> " + String.valueOf(value) + " @ " + String.valueOf(System.nanoTime()));
    }
}
