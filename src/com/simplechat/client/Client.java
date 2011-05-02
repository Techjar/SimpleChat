/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import java.net.*;
import java.io.IOException;
import jline.ConsoleReader;
import com.simplechat.protocol.*;

public class Client {
    ServerData server;
    NameData name;


    public Client(String ip, int port, String name) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        }
        catch(SocketException e) {
            System.err.println("Could not open packet reciever socket.");
            System.exit(0);
        }

        try {
            this.server = new ServerData(InetAddress.getByName(ip), port, socket);
            this.name = new NameData(name, new ConsoleReader());
        }
        catch(UnknownHostException e) {
            System.err.println("Unknown host: " + ip);
            System.exit(0);
        }
        catch(IOException e) {
            System.err.println("An unknown I/O error occurred.");
            System.exit(0);
        }
    }

    public void start() {
        try {
            //System.out.println("Note: Messages are limited to 120 characters!");
            ConsoleReader cr = new ConsoleReader();
            PacketHandler ph = new PacketHandler();
            cr.printString(ConsoleReader.RESET_LINE + "Connecting to server...\n");
            cr.flushConsole();
            this.name.setCr(cr);
            Packet1Join packet = new Packet1Join(this.name.getName());
            ph.sendClientPacket(packet, this.server.getIP(), this.server.getPort(), this.server.getSocket());
            new PacketRecieverThread(this.server, this.name, cr, new ConnectTimeoutThread(cr)).start();
            //Runtime.getRuntime().addShutdownHook(new ClientShutdownThread(this.server, this.name)); // This doesn't ever seem to get called.
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Name too long.");
            System.exit(0);
        }
        catch(IOException e) {
            System.out.println("An unknown I/O error occurred.");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
