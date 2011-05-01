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
import java.util.ArrayList;
import com.simplechat.util.MathHelper;

public class ServerListenerThread extends Thread {
    private int port;
    private List clients;


    public ServerListenerThread(int port) {
        this.port = new MathHelper().clamp(port, 0, 65535);
        this.clients = new ArrayList();
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        }
        catch(IllegalArgumentException e) {
            System.out.println("Invalid port number, please restart specify a valid one.");
            System.exit(0);
        }
        catch(SocketException e) {
            System.err.println("Could not start listener on port " + port + ".");
            System.exit(0);
        }

        System.out.println("Listener started on port " + port + ". Now accepting packets.");
        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(getClients(), socket));
        while(true) {
            try {
                byte[] buffer = new byte[512];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                new PacketHandlerThread(packet, getClients(), socket).start();
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
