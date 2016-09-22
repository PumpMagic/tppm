package com.rmconway.tppm;

import com.sun.glass.ui.SystemClipboard;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.junit.Test;

/**
 * Created by Owner on 9/22/2016.
 */
public class JNATests {
    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface VJoy extends Library {
        VJoy INSTANCE = (VJoy) Native.loadLibrary("vjoyinterface", VJoy.class);

        short GetvJoyVersion();
        boolean vJoyEnabled();
        int GetVJDButtonNumber(int rID);
        boolean GetVJDAxisExist(int rID, int Axis);
        boolean GetVJDAxisMax(int rID, int Axis, NativeLongByReference Max);
        boolean GetVJDAxisMin(int rID, int Axis, NativeLongByReference Min);
    }

    @Test
    public void testJNA() {
        int vjoyDeviceNumber = 1;
        int testAxis = 0x30;

        short vjoyVersion = VJoy.INSTANCE.GetvJoyVersion();
        System.out.println("Using vJoy version " + String.valueOf(vjoyVersion));

        boolean vjoyEnabled = VJoy.INSTANCE.vJoyEnabled();
        System.out.println("vJoy is " + (vjoyEnabled ? "enabled" : "not enabled"));

        int numButtons = VJoy.INSTANCE.GetVJDButtonNumber(vjoyDeviceNumber);
        System.out.println("vJoy device has " + String.valueOf(numButtons) + " buttons");

        boolean axisExists = VJoy.INSTANCE.GetVJDAxisExist(vjoyDeviceNumber, testAxis);
        System.out.println("Test axis " + (axisExists ? "exists" : "does not exist"));

        NativeLongByReference max = new NativeLongByReference(new NativeLong(0));
        boolean returned = VJoy.INSTANCE.GetVJDAxisMax(vjoyDeviceNumber, testAxis, max);
        System.out.println("Test axis max is " + String.valueOf(max.getValue()));
        System.out.println("(Return value was " + String.valueOf(returned) + ")");

        NativeLongByReference min = new NativeLongByReference(new NativeLong(0));
        boolean returned2 = VJoy.INSTANCE.GetVJDAxisMin(vjoyDeviceNumber, testAxis, min);
        System.out.println("Test axis min is " + String.valueOf(min.getValue()));
        System.out.println("(Return value was " + String.valueOf(returned2) + ")");
    }
}
