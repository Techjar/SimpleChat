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

public class Packet0KeepAlive extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String name;
    

    public Packet0KeepAlive(String name) {
        byte[] name2 = name.substring(0, Math.min(name.length(), 120)).getBytes();

        int i = 0;
        this.data[i++] = (byte)0;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)name2.length;
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.type = PacketType.KEEP_ALIVE;
        this.name = name;
    }

    public Packet0KeepAlive(byte[] data) {
        this.data = data;
        this.type = PacketType.KEEP_ALIVE;
        this.name = new String(data, 4, data[3]);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public PacketType getType() {
        return this.type;
    }
}
