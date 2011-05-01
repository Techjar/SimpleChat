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

public class ClientShutdownThread extends Thread {
    private ServerData server;
    private NameData name;


    public ClientShutdownThread(ServerData server, NameData name) {
        this.server = server;
        this.name = name;
    }

    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        Packet2Leave packet = new Packet2Leave(this.name.getName());
        ph.sendClientPacket(packet, this.server.getIP(), this.server.getPort(), this.server.getSocket());
    }
}
