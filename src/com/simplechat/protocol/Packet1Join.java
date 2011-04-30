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

public class Packet1Join extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String name;


    public Packet1Join(String name) {
        byte[] name2 = name.getBytes();

        int i = 0;
        this.data[i++] = (byte)1;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)name2.length;
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.type = PacketType.JOIN;
        this.name = name;
    }

    public Packet1Join(byte[] data) {
        this.data = data;
        this.type = PacketType.JOIN;
        this.name = new String(data, 4, data[3]);
    }
}