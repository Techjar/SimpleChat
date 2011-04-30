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

public class Packet2Leave extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String name;


    public Packet2Leave(String name) {
        byte[] name2 = name.getBytes();

        int i = 0;
        this.data[i++] = (byte)2;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)name2.length;
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.type = PacketType.LEAVE;
        this.name = name;
    }

    public Packet2Leave(byte[] data) {
        this.data = data;
        this.type = PacketType.LEAVE;
        this.name = new String(data, 4, data[3]);
    }
}