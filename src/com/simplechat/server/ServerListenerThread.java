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
import com.simplechat.protocol.Packet.*;

public class ServerListenerThread extends Thread {
    private int port;
    private List clients = null;


    public ServerListenerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(getClients()));
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        }
        catch(SocketException e) {
            System.err.println("Could not start listener on port " + port + ".");
            System.exit(0);
        }

        System.out.println("Listener started on port " + port + ". Now accepting packets.");
        while(true) {
            try {
                byte[] buffer = new byte[512];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                PacketHandlerThread pht = new PacketHandlerThread(packet, getClients());
                pht.start();
            }
            catch (Throwable e) {
                System.err.println("An unknown error occured in the packet acceptor. Program will continue...");
                e.printStackTrace();
            }
        }
    }

    private List getClients() {
        return clients;
    }
}
