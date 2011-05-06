/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.net.*;
import java.util.List;
import java.util.Map;
import com.simplechat.protocol.*;

/**
 * Thread which handles packets sent from clients.
 * @author Techjar
 */
public class PacketHandlerThread extends Thread {
    private DatagramPacket packet;
    private List clients;
    private DatagramSocket socket;
    private DataManager dm;


    /**
     * Creates a new instance of the server's packet handler thread.
     *
     * @param packet packet to process
     * @param clients list of all clients
     * @param socket socket used by the server
     * @param dm DataManager instance used by the server
     */
    public PacketHandlerThread(DatagramPacket packet, List clients, DatagramSocket socket, DataManager dm) {
        this.packet = packet;
        this.clients = clients;
        this.socket = socket;
        this.dm = dm;
    }

    @Override
    public void run() {
        byte[] data = packet.getData();
        PacketHandler ph = new PacketHandler();
        Map<String, String> cfg = new ConfigManager().load();
        PacketType type = ph.getPacketType(data[0]);
        ClientData client = findClient(getPacketName(data));

        //System.out.println("Recieved packet with id: " + data[0]); // DEBUG!
        if(type == PacketType.KEEP_ALIVE) {
            if(client != null) client.startKeepAliveThread(clients);
        }
        else if(type == PacketType.JOIN) {
            Packet1Join packet2 = new Packet1Join(data);
            ClientData newClient = new ClientData(packet2.name, packet2.pass, packet.getAddress(), packet.getPort(), true, this.socket);

            ph.sendPacket(new Packet7Handshake(), newClient, socket);
            if(dm.isBanned(packet2.name) || dm.isIPBanned(packet.getAddress().getHostAddress())) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but was banned.");
                ph.sendPacket(new Packet4Kick("You are banned."), newClient, this.socket);
            }
            else if(getUserCount() >= Integer.parseInt(cfg.get("max-users"))) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but the chat was full.");
                ph.sendPacket(new Packet4Kick("The chat is full."), newClient, this.socket);
            }
            else if(nameTaken(packet2.name)) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but the name was taken.");
                ph.sendPacket(new Packet4Kick("Username taken."), newClient, this.socket);
            }
            else if((Boolean.parseBoolean(cfg.get("require-login")) && packet2.pass.equalsIgnoreCase("")) || (Boolean.parseBoolean(cfg.get("ops-login")) && packet2.pass.equalsIgnoreCase("") && dm.isOp(packet2.name))) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join without a password.");
                ph.sendPacket(new Packet4Kick("You must login with a password."), newClient, this.socket);
            }
            else if(!dm.checkUser(packet2.name, packet2.pass)) {
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") attempted to join but the password was invalid.");
                ph.sendPacket(new Packet4Kick("Your password was invalid."), newClient, this.socket);
            }
            else {
                if((dm.getUser(packet2.name) == null || dm.getUser(packet2.name).equalsIgnoreCase("")) && packet2.pass != null && !packet2.pass.equalsIgnoreCase("")) {
                    dm.addUser(packet2.name, packet2.pass);
                    System.out.println(packet2.name + " has been registered!");
                }
                newClient.startKeepAliveThread(clients);
                newClient.startKeepAliveSendThread();
                clients.add(newClient);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has joined the chat.");
                System.out.println("There are " + this.getUserCount() + " users on the server.");
                ph.sendAllExcludePacket(new Packet5Message(packet2.name + " has joined the chat."), clients, newClient, this.socket);
                ph.sendPacket(new Packet5Message("Welcome to the chat, " + packet2.name + "!"), newClient, this.socket);
            }
        }
        else if(type == PacketType.LEAVE) {
            if(client != null) {
                Packet2Leave packet2 = new Packet2Leave(data);
                client.stopKeepAliveThread();
                client.stopKeepAliveSendThread();
                clients.remove(client);
                System.out.println(packet2.name + " (" + packet.getAddress().getHostAddress() + ") has left the chat. (Client quit)");
                ph.sendAllExcludePacket(new Packet5Message(packet2.name + " has left the chat. (Client quit)"), clients, client, this.socket);
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
                    CommandHandler ch = new CommandHandler(client, clients, this.socket, this.dm);
                    ch.parseCommand(cmd, args);
                }
                else {
                    System.out.println(packet2.name + " said: " + packet2.msg);
                    ph.sendAllExcludePacket(new Packet5Message((dm.isOp(packet2.name) ? "[OP] " : "") + packet2.name + ": " + packet2.msg), clients, client, this.socket);
                }
            }
        }
        else {
            /*String datastr = "";
            for(int i = 0; i < data.length; i++) datastr += data[i];*/
            System.out.println(packet.getAddress().getHostAddress() + " sent a malformed packet: " + data);
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

    private int getUserCount() {
        int i = 0;
        for(i = 0; i < clients.size(); i++){}
        return i;
    }
}
