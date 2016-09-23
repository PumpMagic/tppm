package com.rmconway.tppm.controllers.vjoy.ffi;

/**
 * Created by Owner on 9/23/2016.
 */
public class VJoyJNI {
    static {
        System.loadLibrary("vjoyjni");
    }

    public static VJoyJNI INSTANCE = new VJoyJNI();

    public native short getVJoyVersion();
    public native boolean isVJoyEnabled();
    public native int getNumButtons(int deviceID);
    public native boolean doesDeviceExist(int deviceID);
    public native boolean claimDevice(int deviceID);
    public native boolean releaseDevice(int deviceID);
    public native boolean resetDevice(int deviceID);
    public native boolean setButton(int deviceID, byte buttonID, boolean value);
}
