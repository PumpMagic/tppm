package com.rmconway.tppm.controllers.inputs;

/**
 * A Button is a controller component with two possible states, pressed and not pressed.
 */
public interface Button extends Input {
    void setValue(double value);

    default void setValue(boolean value) {
        if (value) {
            this.setValue(1.0);
        } else {
            this.setValue(0.0);
        }
    }

    default double defaultValue() {
        return 0.0;
    }
}
