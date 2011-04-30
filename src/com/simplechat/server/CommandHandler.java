/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.util.List;
import com.simplechat.protocol.*;

public class CommandHandler {
    private ClientData client;
    private List clients;


    public CommandHandler(ClientData client, List clients) {
        this.client = client;
        this.clients = clients;
    }

    public void parseCommand(String cmd, String[] args) {
        PacketHandler ph = new PacketHandler();
        
        if(cmd.equalsIgnoreCase("quit")) {
            String msg = "";
            for(int i = 0; i < args.length; i++) {
               msg += args[i] + " ";
            }
            msg = msg.trim();

            System.out.println(client.getUsername() + " quit. Reason: " + msg) ;
            Packet4Kick packet = new Packet4Kick("Quitting. Reason: " + msg);
            Packet5Message packet2 = new Packet5Message(client.getUsername() + " quit. (" + msg + ")");
            ph.sendPacket(packet, client);
            clients.remove(client);
            ph.sendAllPacket(packet2, clients);
        }
        else if(cmd.equalsIgnoreCase("stop")) {
            // TODO: Add op check!
            System.out.println(client.getUsername() + " stopped the server.");
            System.exit(0);
        }
        else if(cmd.equalsIgnoreCase("me")) {
            String msg = "";
            for(int i = 0; i < args.length; i++) {
               msg += args[i] + " ";
            }
            msg = msg.trim();

            System.out.println(client.getUsername() + " did action: " + msg);
            Packet5Message packet = new Packet5Message("*" + client.getUsername() + msg);
            ph.sendAllPacket(packet, clients);
        }
        else {
            System.out.println("Command \"" + cmd + "\" not found.");
            Packet5Message packet = new Packet5Message("Unknown command.");
            ph.sendPacket(packet, client);
        }
    }
}
