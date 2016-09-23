package com.rmconway.tppm.controllers.vjoy;

import com.rmconway.tppm.controllers.vjoy.ffi.VJoyFFI;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.fail;

/**
 * Created by Owner on 9/22/2016.
 */
public class VJoyFFITests {
    @Test
    public void testSetButtonTiming() {
        int vjoyDeviceID = 1;
        int buttonsRequired = 1;
        if (!VJoyFFI.isVJoyEnabled() ||
                !VJoyFFI.doesDeviceExist(vjoyDeviceID) ||
                VJoyFFI.getNumButtons(vjoyDeviceID) < buttonsRequired ||
                !VJoyFFI.claimDevice(vjoyDeviceID) ||
                !VJoyFFI.resetDevice(vjoyDeviceID)) {
            fail();
        }

        DescriptiveStatistics timeStats = new DescriptiveStatistics();

        for (int i = 0; i < 100; i++) {
            boolean nextVal = true;
            long startTime = System.nanoTime();
            VJoyFFI.setButton(vjoyDeviceID, (byte) 0x01, nextVal);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            timeStats.addValue(timeTaken);
        }

        System.out.println(timeStats);
    }
}
