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

public class Packet {
    private byte[] data;
    private PacketType type;


    public Packet() {
        this.data = new byte[3];

        int i = 0;
        this.data[i++] = (byte)-1;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.type = PacketType.UNKNOWN;
    }

    public byte[] getData() {
        return this.data;
    }

    public PacketType getType() {
        return this.type;
    }
}
