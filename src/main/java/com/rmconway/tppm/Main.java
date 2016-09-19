package com.rmconway.tppm;

import com.rmconway.tppm.bots.TPPMBot;

public class Main {
    public static void main(String[] args) {
        //@todo get from file
        TPPMBot tppmBot = new TPPMBot("irc.twitch.tv", 6667,
                "twitchplayspapermario",
                "oauth:",
                "#twitchplayspapermario");

        tppmBot.start();
    }
}
