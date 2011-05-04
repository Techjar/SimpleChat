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
import java.util.logging.*;
import java.io.PrintStream;
import java.io.IOException;
import jline.ConsoleReader;
import com.simplechat.protocol.*;
import com.simplechat.util.LoggingOutputStream;
import com.simplechat.util.ClientLogFormatter;
import com.simplechat.util.FileLogFormatter;
import com.simplechat.util.TerminalConsoleHandler;

public class Client {
    public ServerData server;
    public NameData name;
    public ConsoleReader cr;



    public Client(String ip, int port, String name, String pass) {
        try {
            this.cr = new ConsoleReader();
        }
        catch(IOException e) {
            System.err.println("Could not create ConsoleReader.");
            System.exit(0);
        }

        // Save these just in case.
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        try {
            LoggingOutputStream los;
            Handler logFile = new FileHandler("client.log", true);
            logFile.setFormatter(new FileLogFormatter());
            Handler logConsole = new TerminalConsoleHandler(this.cr);
            logConsole.setFormatter(new ClientLogFormatter());

            Logger logger = Logger.getLogger(Client.class.getName());
            logger.setUseParentHandlers(false);
            logger.addHandler(logFile);
            logger.addHandler(logConsole);


            los = new LoggingOutputStream(logger, Level.INFO);
            System.setOut(new PrintStream(los, true));

            los = new LoggingOutputStream(logger, Level.SEVERE);
            System.setErr(new PrintStream(los, true));
        }
        catch(Throwable e) {
            System.err.println("Could not setup logger.");
            e.printStackTrace();
            System.exit(0);
        }


        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        }
        catch(SocketException e) {
            System.err.println("Could not open packet reciever socket.");
            System.exit(0);
        }

        System.out.println("Connecting to server...");
        try {
            this.server = new ServerData(InetAddress.getByName(ip), port, socket);
            this.name = new NameData(name, pass, this.cr);
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
            //System.out.println("Note: Messages are limited to 32,767 characters!");
            PacketHandler ph = new PacketHandler();
            Packet1Join packet = new Packet1Join(this.name.getName(), this.name.getPass());
            ph.sendClientPacket(packet, this.server.getIP(), this.server.getPort(), this.server.getSocket());
            new PacketRecieverThread(this.server, this.name, this.cr, new ConnectTimeoutThread()).start();
            //Runtime.getRuntime().addShutdownHook(new ClientShutdownThread(this.server, this.name)); // This doesn't ever seem to get called.
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Name too long.");
            System.exit(0);
        }
    }
}
