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



    public PacketHandlerThread(DatagramPacket packet, List clients) {
        this.packet = packet;
        this.clients = clients;
    }

    @Override
    public void run() {
        byte[] data = packet.getData();
        PacketHandler ph = new PacketHandler();
        DataManager dm = new DataManager();
        PacketType type = ph.getPacketType(data[0]);
        ClientData client = findClient(packet.getAddress(), packet.getPort());

        if(type == PacketType.KEEP_ALIVE) {
            if(client != null) client.startKeepAliveThread(clients);
        }
        else if(type == PacketType.JOIN) {
            Packet1Join packet2 = new Packet1Join(data);
            ClientData newClient = new ClientData(packet2.name, packet.getAddress(), packet.getPort(), true);
            if(dm.isBanned(packet2.name) || dm.isIPBanned(packet.getAddress().getHostAddress())) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but was banned.");
                Packet4Kick packet3 = new Packet4Kick("You are banned.");
                ph.sendPacket(packet3, newClient);
            }
            if(nameTaken(packet2.name)) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but the name was taken.");
                Packet4Kick packet3 = new Packet4Kick("Username taken.");
                ph.sendPacket(packet3, newClient);
            }
            else {
                newClient.startKeepAliveThread(clients);
                clients.add(newClient);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has joined the chat.");
                Packet5Message packet3 = new Packet5Message(packet2.name + " has joined the chat.");
                ph.sendAllPacket(packet3, clients);
            }
        }
        else if(type == PacketType.LEAVE) {
            if(client != null) {
                Packet2Leave packet2 = new Packet2Leave(data);
                clients.remove(client);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has left the chat. (Client quit)");
                Packet5Message packet3 = new Packet5Message(packet2.name + " has left the chat. (Client quit)");
                ph.sendAllPacket(packet3, clients);
            }
        }
        else if(type == PacketType.CHAT) {
            Packet3Chat packet2 = new Packet3Chat(data);
            if(client != null) {
                if(packet2.msg.startsWith("/")) {
                    String msg = packet2.msg;
                    String cmd = msg.substring(1, msg.indexOf(" ")).trim();
                    String[] args = msg.substring(msg.indexOf(" ")).trim().split(" ");
                    System.out.println(client.getUsername() + " issued command: " + msg.substring(1));
                    CommandHandler ch = new CommandHandler(client, clients);
                    ch.parseCommand(cmd, args);
                }
                else {
                    System.out.println(packet2.name + " said: " + packet2.msg);
                    Packet5Message packet3 = new Packet5Message((dm.isOp(packet2.name) ? "[OP] " : "") + "<" + packet2.name + "> " + packet2.msg);
                    ph.sendAllPacket(packet3, clients);
                }
            }
        }
        else {
            System.out.println(packet.getAddress().getHostAddress() + " sent a malformed packet.");
            System.out.println(data.toString());
        }
    }

    private boolean nameTaken(String name) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client = (ClientData)clients.get(i);
            if(client.getUsername().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private ClientData findClient(InetAddress ip, int port) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client = (ClientData)clients.get(i);
            if(client.getIP() == ip && client.getPort() == port) return client;
        }
        return null;
    }
}
