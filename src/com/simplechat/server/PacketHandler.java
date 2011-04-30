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
import java.io.*;
import java.util.List;
import com.simplechat.protocol.Packet;
import com.simplechat.protocol.PacketType;
import com.simplechat.protocol.ClientData;

public class PacketHandler {
    public PacketHandler() {
    }
    
    public void sendPacket(Packet packet, ClientData client) {
        try {
            if(client.getActiveState()) {
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort());
                socket.send(dpacket);
                socket.close();
            }
        }
        catch (SocketException e) {
            System.err.println("Can't open DatagramSocket to send packet.");
            e.printStackTrace();
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public void sendAllPacket(Packet packet, List clients) {
        try {
            Object[] cl = clients.toArray();
            for(int i = 0; i < cl.length; i++) {
                ClientData client = (ClientData)cl[i];
                if(client.getActiveState()) {
                    DatagramSocket socket = new DatagramSocket();
                    DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort());
                    socket.send(dpacket);
                    socket.close();
                }
            }
        }
        catch (SocketException e) {
            System.err.println("Can't open DatagramSocket to send packet.");
            e.printStackTrace();
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("An unknown I/O error occurred.");
            e.printStackTrace();
        }
    }

    public void sendClientPacket(Packet packet, InetAddress ip, int port) {
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length, ip, port);
            socket.send(dpacket);
            socket.close();
        }
        catch (SocketException e) {
            System.err.println("Can't open DatagramSocket to send packet.");
            e.printStackTrace();
            System.exit(1);
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
            default: return PacketType.UNKNOWN;
        }
    }
}
