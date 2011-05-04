/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 1, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import java.net.*;
import jline.ConsoleReader;

public class PacketRecieverThread extends Thread {
    private ServerData server;
    private NameData name;
    private ConsoleReader cr;
    private ConnectTimeoutThread ctt;


    public PacketRecieverThread(ServerData server, NameData name, ConsoleReader cr, ConnectTimeoutThread ctt) {
        this.server = server;
        this.name = name;
        this.cr = cr;
        this.ctt = ctt;
    }

    @Override
    public void run() {
        DatagramSocket socket = this.server.getSocket();
        this.ctt.start();
        byte[] buffer = new byte[262144];
        while(true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                new PacketHandlerThread(new DatagramPacket(packet.getData().clone(), packet.getData().clone().length, InetAddress.getByName(packet.getAddress().getHostAddress()), packet.getPort()), this.server, this.name, this.cr, this.ctt).start();
            }
            catch (Throwable e) {
                System.err.println("An unknown error occured in the packet acceptor. Program will continue...");
                e.printStackTrace();
            }
        }
    }
}
