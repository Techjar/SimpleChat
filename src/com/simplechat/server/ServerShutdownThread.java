/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.net.DatagramSocket;
import java.util.List;
import com.simplechat.protocol.*;

public class ServerShutdownThread extends Thread {
    private List clients;
    private DatagramSocket socket;


    public ServerShutdownThread(List clients, DatagramSocket socket) {
        this.clients = clients;
        this.socket = socket;
    }

    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        ph.sendAllPacket(new Packet4Kick("Server shutting down."), clients, this.socket);
    }
}
