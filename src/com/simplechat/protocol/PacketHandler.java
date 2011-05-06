/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

import java.net.*;
import java.io.*;
import java.util.List;

/**
 * Handler for sending out packets.
 * @author Techjar
 */
public class PacketHandler {
    /**
     * Creates a new instance of the packet handler.
     */
    public PacketHandler() {
    }

    /**
     * Sends a packet to a single client.
     *
     * @param packet the packet to be sent
     * @param client client to send the packet to
     * @param socket socket to use for sending the packet
     */
    public void sendPacket(Packet packet, ClientData client, DatagramSocket socket) {
        try {
            if(client.getActiveState()) {
                socket.send(new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort()));
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

    /**
     * Sends a packet to all clients.
     *
     * @param packet the packet to be sent
     * @param clients list of all clients
     * @param socket socket to use for sending the packet
     */
    public void sendAllPacket(Packet packet, List clients, DatagramSocket socket) {
        try {
            for(int i = 0; i < clients.size(); i++) {
                ClientData client = (ClientData)clients.get(i);
                if(client.getActiveState()) {
                    socket.send(new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort()));
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

    /**
     * Sends a packet to all clients except the one specified.
     *
     * @param packet the packet to be sent
     * @param clients list of all clients
     * @param exclude client to not be sent the packet
     * @param socket socket to use for sending the packet
     */
    public void sendAllExcludePacket(Packet packet, List clients, ClientData exclude, DatagramSocket socket) {
        try {
            for(int i = 0; i < clients.size(); i++) {
                ClientData client = (ClientData)clients.get(i);
                if(client.getActiveState() && client != exclude) {
                    socket.send(new DatagramPacket(packet.getData(), packet.getData().length, client.getIP(), client.getPort()));
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

    /**
     * Sends a packet from the client to the server.
     *
     * @param packet the packet to be sent
     * @param ip IP address of the server
     * @param port port of the server
     * @param socket socket to use for sending the packet
     */
    public void sendClientPacket(Packet packet, InetAddress ip, int port, DatagramSocket socket) {
        try {
            socket.send(new DatagramPacket(packet.getData(), packet.getData().length, ip, port));
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

    /**
     * Determines the type of a packet from it's ID.
     *
     * @param id ID of the packet
     * @return PacketType Enum which matches the ID
     */
    public PacketType getPacketType(int id) {
        switch(id) {
            case 0: return PacketType.KEEP_ALIVE;
            case 1: return PacketType.JOIN;
            case 2: return PacketType.LEAVE;
            case 3: return PacketType.CHAT;
            case 4: return PacketType.KICK;
            case 5: return PacketType.MESSAGE;
            case 6: return PacketType.NAME_CHANGE;
            case 7: return PacketType.HANDSHAKE;
            case 8: return PacketType.PASSWORD_CHANGE;
            default: return PacketType.UNKNOWN;
        }
    }
}
