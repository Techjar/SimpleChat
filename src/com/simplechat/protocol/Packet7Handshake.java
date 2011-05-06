/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Handshake packet, sent by server to tell client it's connected.
 * @author Techjar
 */
public class Packet7Handshake extends Packet {
    private byte[] data;
    private PacketType type;
    

    /**
     * Creates a new instance of this packet.
     */
    public Packet7Handshake() {
        this.data = new byte[3];
        
        int i = 0;
        this.data[i++] = (byte)7;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.type = PacketType.HANDSHAKE;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet7Handshake(byte[] data) {
        this.data = data;
        this.type = PacketType.HANDSHAKE;
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
