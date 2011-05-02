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

public class Packet7Handshake extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    

    public Packet7Handshake() {
        int i = 0;
        this.data[i++] = (byte)7;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.type = PacketType.KEEP_ALIVE;
    }

    public Packet7Handshake(byte[] data) {
        this.data = data;
        this.type = PacketType.KEEP_ALIVE;
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
