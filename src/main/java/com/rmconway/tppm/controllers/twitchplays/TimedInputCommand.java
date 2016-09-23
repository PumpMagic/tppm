package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.Input;

import java.util.Comparator;

/**
 * A TimedInputCommand is
 */
public class TimedInputCommand {
    public Input target;
    public double value;
    public long offset;
    public long duration;
    public boolean isStart;

    private static class TimedInputCommandComparator implements Comparator<TimedInputCommand> {
        @Override
        public int compare(TimedInputCommand c1, TimedInputCommand c2) {
            if (c1.offset < c2.offset) {
                return -1;
            } else if (c1.offset >= c2.offset) {
                return 1;
            }

            return 0;
        }
    }
    public static Comparator<TimedInputCommand> comparator = new TimedInputCommandComparator();

    public TimedInputCommand(Input target, double value, long offset, long duration, boolean isStart) {
        this.target = target;
        this.value = value;
        this.offset = offset;
        this.duration = duration;
        this.isStart = isStart;
    }
}
