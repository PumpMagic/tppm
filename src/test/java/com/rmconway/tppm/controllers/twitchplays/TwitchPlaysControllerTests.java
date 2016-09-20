package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.models.SNESController;
import com.rmconway.tppm.controllers.twitchplays.games.InputProfiles;
import org.junit.Test;

/**
 * Created by owner on 9/19/16.
 */
public class TwitchPlaysControllerTests {
    @Test
    public void testController() throws Exception {
        SNESController snesController = SNESController.build(0).get();
        InputProfile inputProfile = InputProfiles.SuperMarioRPG(snesController);

        TwitchPlaysController controller = new TwitchPlaysController(snesController, inputProfile);

        controller.handleText("a b x y");
        controller.handleText("up down left right");
        controller.handleText("l r select start");

        Thread.sleep(2000);
    }
}
