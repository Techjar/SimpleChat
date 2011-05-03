/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.protocol;

public class Packet6NameChange extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String name;


    public Packet6NameChange(String name) {
        byte[] name2 = name.substring(0, Math.min(name.length(), Byte.MAX_VALUE)).getBytes();
        this.data = new byte[name2.length + 5];

        int i = 0;
        this.data[i++] = (byte)6;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((name2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(name2.length & 0xFF);
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.type = PacketType.NAME_CHANGE;
        this.name = name;
    }

    public Packet6NameChange(byte[] data) {
        int nameL = ((data[3] << 8) | (data[4] & 0xFF));
        this.data = data;
        this.type = PacketType.NAME_CHANGE;
        this.name = new String(data, 5, nameL);
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
