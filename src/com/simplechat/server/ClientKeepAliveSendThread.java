/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.net.DatagramSocket;
import com.simplechat.protocol.*;

/**
 * Thread which sends keep alive packets to client.
 * @author Techjar
 */
public class ClientKeepAliveSendThread extends Thread {
    private ClientData client;
    private DatagramSocket socket;
    private boolean alive;


    /**
     * Creates a new instance of the server's keep alive packet sender thread.
     *
     * @param client ClientData instance for the client
     * @param socket socket used by the server
     */
    public ClientKeepAliveSendThread(ClientData client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
        this.alive = true;
    }

    /**
     * Runs this thread.
     */
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

    /**
     * Custom method to kill this thread.
     */
    public synchronized void kill() {
        this.alive = false;
        this.interrupt();
    }
}
