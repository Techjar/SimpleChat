/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.net.DatagramSocket;
import java.util.List;
import com.simplechat.protocol.*;

/**
 * Thread started on server shutdown, kicks all users with a "Server shutting down." message.
 * @author Techjar
 */
public class ServerShutdownThread extends Thread {
    private List clients;
    private DatagramSocket socket;


    /**
     * Creates a new instance of the server shutdown handler thread.
     *
     * @param clients list of all clients
     * @param socket socket used by the server
     */
    public ServerShutdownThread(List clients, DatagramSocket socket) {
        this.clients = clients;
        this.socket = socket;
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        PacketHandler ph = new PacketHandler();
        ph.sendAllPacket(new Packet4Kick("Server shutting down."), clients, this.socket);
    }
}
