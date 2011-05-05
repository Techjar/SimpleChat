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
                Thread.sleep(10000);
                PacketHandler ph = new PacketHandler();
                ph.sendClientPacket(new Packet0KeepAlive(this.name.getName()), server.getIP(), server.getPort(), this.server.getSocket());
            }
            catch(InterruptedException e) {
                System.err.println("ClientKeepAliveThread was interrupted.");
                System.exit(0);
            }
        }
    }
}
