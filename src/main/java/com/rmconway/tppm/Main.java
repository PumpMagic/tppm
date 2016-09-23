package com.rmconway.tppm;

import com.rmconway.tppm.bots.TwitchPlaysBot;
import com.rmconway.tppm.controllers.models.SNESController;
import com.rmconway.tppm.controllers.twitchplays.InputProfile;
import com.rmconway.tppm.controllers.twitchplays.TwitchPlaysController;
import com.rmconway.tppm.controllers.twitchplays.games.InputProfiles;

public class Main {
    public static void main(String[] args) {
        SNESController snesController = SNESController.build(1).get();
        InputProfile inputProfile = InputProfiles.SuperMarioRPG(snesController);
        TwitchPlaysController controller = new TwitchPlaysController(snesController, inputProfile);

        TwitchPlaysBot twitchPlaysBot = new TwitchPlaysBot("irc.twitch.tv", 6667,
                "twitchplayspapermario",
                "oauth:qnc4jo957a7csf18rmegif7bx2bnd2",
                "#twitchplayspapermario",
                (sender, message) -> {
                    System.out.println("Got message from " + sender + ": " + message);
                    boolean wasCommandChain = controller.handleText(message);
                    System.out.println("That " + (wasCommandChain ? "was" : "wasn't") + " a command chain!");
                });

        twitchPlaysBot.start();
    }
}
