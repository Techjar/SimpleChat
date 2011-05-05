/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 2, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.net.DatagramSocket;
import com.simplechat.protocol.*;

public class ClientKeepAliveSendThread extends Thread {
    private String name;
    private ClientData client;
    private DatagramSocket socket;
    private boolean alive;


    public ClientKeepAliveSendThread(String name, ClientData client, DatagramSocket socket) {
        this.name = name;
        this.client = client;
        this.socket = socket;
        this.alive = true;
    }

    @Override
    public void run() {
        synchronized(this) {
            while(this.alive) {
                try {
                    Thread.sleep(10000);
                    PacketHandler ph = new PacketHandler();
                    ph.sendPacket(new Packet0KeepAlive("Server"), client, this.socket);
                }
                catch(InterruptedException e) {
                    //System.err.println("ClientKeepAliveSendThread was interrupted.");
                }
            }
        }
    }

    public synchronized void kill() {
        this.alive = false;
        this.interrupt();
    }
}
