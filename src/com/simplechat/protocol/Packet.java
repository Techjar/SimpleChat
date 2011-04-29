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

public class Packet {
    public byte[] createPacket(int type, String name, String msg){
        byte [] data = new byte[512];
        byte[] name2 = name.getBytes();
        byte[] msg2 = msg.getBytes();

        int i = 0;
        data[i++] = (byte)type;
        data[i++] = (byte)'R';
        data[i++] = (byte)'E';
        data[i++] = (byte)name2.length;
        for(int n = 0; n < name2.length; n++) data[i++] = name2[n];
        data[i++] = (byte)msg2.length;
        for(int n = 0; n < msg2.length; n++) data[i++] = msg2[n];

        return data;
    }

    public void sendPacket(byte[] data, ClientData client) {
        try {
            if(client.getActiveState()) {
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket packet = new DatagramPacket(data, data.length, client.getIP(), client.getPort());
                socket.send(packet);
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

    public void sendAllPacket(byte[] data, List clients) {
        try {
            Object[] cl = clients.toArray();
            for(int i = 0; i < cl.length; i++) {
                ClientData client = (ClientData)cl[i];
                if(client.getActiveState()) {
                    DatagramSocket socket = new DatagramSocket();
                    DatagramPacket packet = new DatagramPacket(data, data.length, client.getIP(), client.getPort());
                    socket.send(packet);
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
}
