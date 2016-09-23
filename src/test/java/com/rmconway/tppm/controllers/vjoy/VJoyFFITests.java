package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNA;
import com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNI;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * Created by Owner on 9/22/2016.
 */
public class VJoyFFITests {
    @Test
    public void testSetButtonTimingJNI() {
        int vjoyDeviceID = 1;
        int buttonsRequired = 1;
        if (!VJoyJNI.INSTANCE.isVJoyEnabled() ||
                !VJoyJNI.INSTANCE.doesDeviceExist(vjoyDeviceID) ||
                VJoyJNI.INSTANCE.getNumButtons(vjoyDeviceID) < buttonsRequired ||
                !VJoyJNI.INSTANCE.claimDevice(vjoyDeviceID) ||
                !VJoyJNI.INSTANCE.resetDevice(vjoyDeviceID)) {
            fail();
        }

        DescriptiveStatistics timeStats = new DescriptiveStatistics();

        for (int i = 0; i < 1000; i++) {
            boolean nextVal = true;
            long startTime = System.nanoTime();
            VJoyJNI.INSTANCE.setButton(vjoyDeviceID, (byte) 0x01, nextVal);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            timeStats.addValue(timeTaken);
        }

        VJoyJNI.INSTANCE.releaseDevice(vjoyDeviceID);

        System.out.println("JNI:");
        System.out.println(timeStats);
    }

    @Test
    public void testSetButtonTimingJNA() {
        int vjoyDeviceID = 1;
        int buttonsRequired = 1;
        if (!VJoyJNA.isVJoyEnabled() ||
                !VJoyJNA.doesDeviceExist(vjoyDeviceID) ||
                VJoyJNA.getNumButtons(vjoyDeviceID) < buttonsRequired ||
                !VJoyJNA.claimDevice(vjoyDeviceID) ||
                !VJoyJNA.resetDevice(vjoyDeviceID)) {
            fail();
        }

        DescriptiveStatistics timeStats = new DescriptiveStatistics();

        for (int i = 0; i < 1000; i++) {
            boolean nextVal = true;
            long startTime = System.nanoTime();
            VJoyJNA.setButton(vjoyDeviceID, (byte) 0x01, nextVal);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            timeStats.addValue(timeTaken);
        }

        VJoyJNA.releaseDevice(vjoyDeviceID);

        System.out.println("JNA:");
        System.out.println(timeStats);
    }
}
