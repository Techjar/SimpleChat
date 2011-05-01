/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import com.simplechat.protocol.*;

public class ClientKeepAliveThread extends Thread {
    private NameData name;
    private ServerData server;


    public ClientKeepAliveThread(ServerData server, NameData name) {
        this.name = name;
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(30000);
                PacketHandler ph = new PacketHandler();
                Packet0KeepAlive packet = new Packet0KeepAlive(this.name.getName());
                ph.sendClientPacket(packet, server.getIP(), server.getPort(), this.server.getSocket());
            }
            catch(InterruptedException e) {
                //System.err.println("ClientKeepAliveThread was interrupted.");
            }
        }
    }
}
