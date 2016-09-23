package com.rmconway.tppm.controllers.vjoy.ffi;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

/**
 * A vJoy FFI wrapped in a Java-friendly package. Only wraps a subset of vJoy functionality.
 */
public class VJoyJNA {
    private interface VJoyNativeAPI extends Library {
        VJoyNativeAPI INSTANCE = (VJoyNativeAPI) Native.loadLibrary("vjoyinterface", VJoyNativeAPI.class);

        short GetvJoyVersion();
        boolean vJoyEnabled();
        int GetVJDButtonNumber(int rID);
        boolean GetVJDAxisExist(int rID, int Axis);
        boolean GetVJDAxisMax(int rID, int Axis, NativeLongByReference Max);
        boolean GetVJDAxisMin(int rID, int Axis, NativeLongByReference Min);
        boolean isVJDExists(int rID);
        boolean AcquireVJD(int rID);
        boolean RelinquishVJD(int rID);
        boolean ResetVJD(int rID);
        boolean SetAxis(NativeLong value, int rID, int Axis);
        boolean SetBtn(boolean value, int rID, byte nBtn);
    }

    public static short getVJoyVersion() {
        return VJoyNativeAPI.INSTANCE.GetvJoyVersion();
    }

    public static boolean isVJoyEnabled() {
        return VJoyNativeAPI.INSTANCE.vJoyEnabled();
    }

    public static int getNumButtons(int deviceID) {
        return VJoyNativeAPI.INSTANCE.GetVJDButtonNumber(deviceID);
    }

    public static boolean doesDeviceExist(int deviceID) {
        return VJoyNativeAPI.INSTANCE.isVJDExists(deviceID);
    }

    public static boolean claimDevice(int deviceID) {
        return VJoyNativeAPI.INSTANCE.AcquireVJD(deviceID);
    }

    public static boolean releaseDevice(int deviceID) {
        return VJoyNativeAPI.INSTANCE.RelinquishVJD(deviceID);
    }

    public static boolean resetDevice(int deviceID) {
        return VJoyNativeAPI.INSTANCE.ResetVJD(deviceID);
    }

    public static boolean setButton(int deviceID, byte buttonID, boolean value) {
        return VJoyNativeAPI.INSTANCE.SetBtn(value, deviceID, buttonID);
    }
}
