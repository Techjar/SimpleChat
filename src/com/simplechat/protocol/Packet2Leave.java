/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Leave packet, sent by client upon client-side leave operation.
 * @author Techjar
 */
public class Packet2Leave extends Packet {
    private byte[] data;
    private PacketType type;
    /**
     * Name of the client.
     */
    public String name;


    /**
     * Creates a new instance of this packet.
     *
     * @param name name of client
     */
    public Packet2Leave(String name) {
        byte[] name2 = name.substring(0, Math.min(name.length(), Short.MAX_VALUE)).getBytes();
        this.data = new byte[name2.length + 5];

        int i = 0;
        this.data[i++] = (byte)2;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((name2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(name2.length & 0xFF);
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.type = PacketType.LEAVE;
        this.name = name;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet2Leave(byte[] data) {
        int nameL = ((data[3] << 8) | (data[4] & 0xFF));
        this.data = data;
        this.type = PacketType.LEAVE;
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
