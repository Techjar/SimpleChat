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

public class ClientKeepAliveThread extends Thread {
    private String name;
    private ClientData client;
    private List clients;
    private DatagramSocket socket;


    public ClientKeepAliveThread(String name, ClientData client, List clients, DatagramSocket socket) {
        this.name = name;
        this.client = client;
        this.clients = clients;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(60000);
            clients.remove(client);
            System.out.println(name + " left the chat. (Ping timeout)");
            PacketHandler ph = new PacketHandler();
            Packet5Message packet = new Packet5Message(name + " left the chat. (Ping timeout)");
            ph.sendAllPacket(packet, clients, this.socket);
        }
        catch(InterruptedException e) {
            //System.err.println("ClientKeepAliveThread was interrupted.");
        }
    }
}
