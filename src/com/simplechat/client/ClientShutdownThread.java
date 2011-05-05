/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 1, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import com.simplechat.protocol.*;

public class ClientShutdownThread extends Thread { // This class isn't actually used now, but it's left in for later use.
    private ServerData server;
    private NameData name;


    public ClientShutdownThread(ServerData server, NameData name) {
        this.server = server;
        this.name = name;
    }

    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        ph.sendClientPacket(new Packet2Leave(this.name.getName()), this.server.getIP(), this.server.getPort(), this.server.getSocket());
    }
}
