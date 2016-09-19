package com.rmconway.tppm.controllers.twitchplays;

/**
 * Something TextOperated can be controlled by passing it text.
 */
public interface TextOperated {
    boolean handleText(String text);
}
