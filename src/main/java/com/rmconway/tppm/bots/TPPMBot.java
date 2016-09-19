package com.rmconway.tppm.bots;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

/**
 * Created by owner on 9/15/16.
 */
public class TPPMBot extends PircBot {
    private String server;
    private int port;
    private String auth;
    private String listenChannel;

    public TPPMBot(String server, int port, String username, String auth, String listenChannel) {
        this.setVerbose(true);
        this.setName(username);
        this.setLogin(username);

        this.server = server;
        this.port = port;
        this.auth = auth;
        this.listenChannel = listenChannel;
    }

    public void start() {
        try {
            this.connect(this.server, this.port, this.auth);
        } catch (NickAlreadyInUseException e) {

        } catch (IrcException e) {

        } catch (IOException e) {

        }
    }

    @Override
    protected void onConnect() {
        this.joinChannel(this.listenChannel);
    }

    //@todo improve this: what if the reconnect fails?
    //instead of immediately attempting to reconnect, consider spawning a reconnect thread that terminates on success
    @Override
    protected void onDisconnect() {
        try {
            this.reconnect();
        } catch (IrcException e) {

        } catch (IOException e) {

        }
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        System.out.println("Got message from user " + sender + ": " + message);
    }
}
