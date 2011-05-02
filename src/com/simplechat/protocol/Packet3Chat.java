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

public class Packet3Chat extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String name;
    public String msg;


    public Packet3Chat(String name, String msg) {
        byte[] name2 = name.substring(0, Math.min(name.length(), 120)).getBytes();
        byte[] msg2 = msg.substring(0, Math.min(msg.length(), 120)).getBytes();

        int i = 0;
        this.data[i++] = (byte)3;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)name2.length;
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.data[i++] = (byte)msg2.length;
        for(int j = 0; j < msg2.length; j++) this.data[i++] = msg2[j];
        this.type = PacketType.CHAT;
        this.name = name;
        this.msg = msg;
    }

    public Packet3Chat(byte[] data) {
        this.data = data;
        this.type = PacketType.CHAT;
        this.name = new String(data, 4, data[3]);
        this.msg = new String(data, 5+data[3], data[4+data[3]]);
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
