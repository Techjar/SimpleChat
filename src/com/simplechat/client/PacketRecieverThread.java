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
import com.simplechat.protocol.*;

public class PacketRecieverThread extends Thread {
    private ServerData server;
    private NameData name;
    private ConsoleReader cr;


    public PacketRecieverThread(ServerData server, NameData name, ConsoleReader cr) {
        this.server = server;
        this.name = name;
        this.cr = cr;
    }

    @Override
    public void run() {
        DatagramSocket socket = this.server.getSocket();
        while(true) {
            try {
                byte[] buffer = new byte[512];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                new PacketHandlerThread(packet, this.server, this.name, this.cr).start();
            }
            catch (Throwable e) {
                System.err.println("An unknown error occured in the packet acceptor. Program will continue...");
                e.printStackTrace();
            }
        }
    }
}
