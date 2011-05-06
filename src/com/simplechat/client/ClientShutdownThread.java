/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

import com.simplechat.protocol.*;

/**
 * Called on client shutdown. Currently unused!
 * @author Techjar
 */
public class ClientShutdownThread extends Thread { // This class isn't actually used now, but it's left in for later use.
    private ServerData server;
    private NameData name;


    /**
     * Creates a new instance of the thread called on client shutdown. Currently unused.
     *
     * @param server information about the server
     * @param name information about the client
     */
    public ClientShutdownThread(ServerData server, NameData name) {
        this.server = server;
        this.name = name;
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        ph.sendClientPacket(new Packet2Leave(this.name.getName()), this.server.getIP(), this.server.getPort(), this.server.getSocket());
    }
}
