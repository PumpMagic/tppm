package com.rmconway.tppm.controllers.inputs;

import java.util.Optional;
import java.util.Set;

/**
 * If something is HasInputs, it has inputs with modifiable state, e.g. a video game controller
 */
public interface HasInputs {
    Set<? extends Input> getInputs();

    default Optional<Input> getInputByName(String name) {
        for (Input i : this.getInputs()) {
            if (i.getName().equals(name)) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }
}
