package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNA;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * Created by Owner on 9/22/2016.
 */
public class VJoyFFITests {
    @Test
    public void testSetButtonTiming() {
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

        for (int i = 0; i < 100; i++) {
            boolean nextVal = true;
            long startTime = System.nanoTime();
            VJoyJNA.setButton(vjoyDeviceID, (byte) 0x01, nextVal);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            timeStats.addValue(timeTaken);
        }

        System.out.println(timeStats);
    }
}
