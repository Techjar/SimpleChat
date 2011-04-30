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

import java.net.*;
import java.util.List;
import com.simplechat.protocol.*;

public class ServerShutdownThread extends Thread {
    private List clients;


    public ServerShutdownThread(List clients) {
        this.clients = clients;
    }

    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        Packet4Kick packet = new Packet4Kick("Server shutting down.");
        ph.sendAllPacket(packet, clients);
    }
}
