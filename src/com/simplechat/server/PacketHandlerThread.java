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
import com.simplechat.protocol.*;

public class PacketHandlerThread extends Thread {
    private DatagramPacket packet;
    private List clients;
    private DatagramSocket socket;



    public PacketHandlerThread(DatagramPacket packet, List clients, DatagramSocket socket) {
        this.packet = packet;
        this.clients = clients;
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] data = packet.getData();
        PacketHandler ph = new PacketHandler();
        DataManager dm = new DataManager();
        PacketType type = ph.getPacketType(data[0]);
        ClientData client = findClient(getPacketName(data));

        //System.out.println("Recieved packet with id: " + data[0]); // DEBUG!
        if(type == PacketType.KEEP_ALIVE) {
            if(client != null) client.startKeepAliveThread(clients);
        }
        else if(type == PacketType.JOIN) {
            Packet1Join packet2 = new Packet1Join(data);
            ClientData newClient = new ClientData(packet2.name, packet.getAddress(), packet.getPort(), true, this.socket);

            Packet7Handshake packeth = new Packet7Handshake();
            ph.sendPacket(packeth, newClient, socket);
            if(dm.isBanned(packet2.name) || dm.isIPBanned(packet.getAddress().getHostAddress())) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but was banned.");
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e) {
                    //e.printStackTrace();
                }
                Packet4Kick packet3 = new Packet4Kick("You are banned.");
                ph.sendPacket(packet3, newClient, this.socket);
            }
            else if(nameTaken(packet2.name)) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but the name was taken.");
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e) {
                    //e.printStackTrace();
                }
                Packet4Kick packet3 = new Packet4Kick("Username taken.");
                ph.sendPacket(packet3, newClient, this.socket);
            }
            else {
                newClient.startKeepAliveThread(clients);
                newClient.startKeepAliveSendThread();
                clients.add(newClient);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has joined the chat.");
                Packet5Message packet3 = new Packet5Message(packet2.name + " has joined the chat.");
                ph.sendAllExcludePacket(packet3, clients, newClient, this.socket);
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e) {
                    //e.printStackTrace();
                }
                Packet5Message packet4 = new Packet5Message("Welcome to the chat, " + packet2.name + "!");
                ph.sendPacket(packet4, newClient, this.socket);
            }
        }
        else if(type == PacketType.LEAVE) {
            if(client != null) {
                Packet2Leave packet2 = new Packet2Leave(data);
                client.stopKeepAliveThread();
                client.stopKeepAliveSendThread();
                clients.remove(client);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has left the chat. (Client quit)");
                Packet5Message packet3 = new Packet5Message(packet2.name + " has left the chat. (Client quit)");
                ph.sendAllPacket(packet3, clients, this.socket);
            }
        }
        else if(type == PacketType.CHAT) {
            Packet3Chat packet2 = new Packet3Chat(data);
            if(client != null) {
                if(packet2.msg.startsWith("/")) {
                    String msg = packet2.msg.trim();
                    String cmd;
                    String[] args;
                    if(msg.split(" ").length > 1) {
                        cmd = msg.substring(1, msg.indexOf(" ")).trim();
                        args = msg.substring(msg.indexOf(" ")).trim().split(" ");
                    }
                    else {
                        cmd = msg.substring(1);
                        args = new String[0];
                    }
                    System.out.println(client.getUsername() + " issued command: " + msg.substring(1));
                    CommandHandler ch = new CommandHandler(client, clients, this.socket);
                    ch.parseCommand(cmd, args);
                }
                else {
                    System.out.println(packet2.name + " said: " + packet2.msg);
                    Packet5Message packet3 = new Packet5Message((dm.isOp(packet2.name) ? "[OP] " : "") + packet2.name + ": " + packet2.msg);
                    ph.sendAllExcludePacket(packet3, clients, client, this.socket);
                }
            }
        }
        else {
            /*String datastr = "";
            for(int i = 0; i < data.length; i++) datastr += data[i];*/
            System.out.println(packet.getAddress().getHostAddress() + " sent a malformed packet; " + data);
            //System.out.println(datastr);
        }
    }

    private boolean nameTaken(String name) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client = (ClientData)clients.get(i);
            if(client.getUsername().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private String getPacketName(byte[] data) {
        return new String(data, 5, ((data[3] << 8) | (data[4] & 0xFF)));
    }

    private ClientData findClient(String name) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client = (ClientData)clients.get(i);
            if(client.getUsername().equalsIgnoreCase(name)) return client;
        }
        return null;
    }
}
