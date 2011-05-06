/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Default packet class, superclass of all packets.
 * @author Techjar
 */
public class Packet {
    private byte[] data;
    private PacketType type;


    /**
     * Creates a new instance of this packet.
     */
    public Packet() {
        this.data = new byte[3];

        int i = 0;
        this.data[i++] = (byte)-1;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.type = PacketType.UNKNOWN;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet(byte[] data) {
        this.data = data;
        this.type = PacketType.UNKNOWN;
    }

    /**
     * Retrieves this packet's data.
     *
     * @return the packet data
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Retrieves this packet's type.
     *
     * @return the packet type
     */
    public PacketType getType() {
        return this.type;
    }
}
