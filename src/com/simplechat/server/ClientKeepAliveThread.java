/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.net.DatagramSocket;
import java.util.List;
import com.simplechat.protocol.*;

/**
 * Thread for client ping timeout, gets reset when a keep alive packet is received.
 * @author Techjar
 */
public class ClientKeepAliveThread extends Thread {
    private ClientData client;
    private List clients;
    private DatagramSocket socket;


    /**
     * Creates a new instance of the server's keep alive timeout thread.
     *
     * @param client ClientData instance for the client
     * @param clients list of all clients
     * @param socket socket used by the server
     */
    public ClientKeepAliveThread(ClientData client, List clients, DatagramSocket socket) {
        this.client = client;
        this.clients = clients;
        this.socket = socket;
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(60000);
            clients.remove(client);
            System.out.println(client.getUsername() + " left the chat. (Ping timeout)");
            PacketHandler ph = new PacketHandler();
            ph.sendAllPacket(new Packet5Message(client.getUsername() + " left the chat. (Ping timeout)"), clients, this.socket);
        }
        catch(InterruptedException e) {
            //System.err.println("ClientKeepAliveThread was interrupted.");
        }
    }
}
