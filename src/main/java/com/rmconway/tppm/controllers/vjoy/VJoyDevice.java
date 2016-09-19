package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.inputs.HasInputs;

/**
 * A VJoyDevice is a virtual HID as implemented in vJoy.
 */
public interface VJoyDevice extends HasInputs {
    int getVJoyDeviceID();
}
