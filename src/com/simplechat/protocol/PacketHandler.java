/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version
 */


package com.simplechat.protocol;

import java.net.*;
import java.io.*;
import java.util.List;

public class PacketHandler {
    public PacketHandler() {
    }

    public void sendPacket(Packet packet, ClientData client, DatagramSocket socket) {
        try {
            if(client.getActiveState()) {
                DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort());
                socket.send(dpacket);
            }
        }
        catch (SocketException e) {
            System.err.println("Can't send packet through socket.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public void sendAllPacket(Packet packet, List clients, DatagramSocket socket) {
        try {
            for(int i = 0; i < clients.size(); i++) {
                ClientData client = (ClientData)clients.get(i);
                if(client.getActiveState()) {
                    DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort());
                    socket.send(dpacket);
                }
            }
        }
        catch (SocketException e) {
            System.err.println("Can't send packet through socket.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public void sendAllExcludePacket(Packet packet, List clients, ClientData exclude, DatagramSocket socket) {
        try {
            for(int i = 0; i < clients.size(); i++) {
                ClientData client = (ClientData)clients.get(i);
                if(client.getActiveState() && client != exclude) {
                    DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort());
                    socket.send(dpacket);
                }
            }
        }
        catch (SocketException e) {
            System.err.println("Can't send packet through socket.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public void sendClientPacket(Packet packet, InetAddress ip, int port, DatagramSocket socket) {
        try {
            DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, ip, port);
            socket.send(dpacket);
        }
        catch (SocketException e) {
            System.err.println("Can't send packet through socket.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public PacketType getPacketType(int id) {
        switch(id) {
            case 0: return PacketType.KEEP_ALIVE;
            case 1: return PacketType.JOIN;
            case 2: return PacketType.LEAVE;
            case 3: return PacketType.CHAT;
            case 4: return PacketType.KICK;
            case 5: return PacketType.MESSAGE;
            case 6: return PacketType.NAME_CHANGE;
            default: return PacketType.UNKNOWN;
        }
    }
}
