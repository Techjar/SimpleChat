/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

import com.simplechat.protocol.*;

/**
 * Sends keep alive packets to the server.
 * @author Techjar
 */
public class ClientKeepAliveThread extends Thread {
    private NameData name;
    private ServerData server;


    /**
     * Creates a new instance of the keep alive packet sender thread.
     *
     * @param server information about the server
     * @param name information about the client
     */
    public ClientKeepAliveThread(ServerData server, NameData name) {
        this.name = name;
        this.server = server;
    }

    /**
     * Runs this thread.
     */
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
