package com.rmconway.tppm.controllers.inputs;

/**
 * An Input is some named controller component with a settable value.
 */
public interface Input {
    String getName();
    void setValue(double value);
    double defaultValue();
}
