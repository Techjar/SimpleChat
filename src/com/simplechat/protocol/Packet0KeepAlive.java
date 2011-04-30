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
    

    public Packet0KeepAlive() {
        int i = 0;
        this.data[i++] = (byte)0;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.type = PacketType.KEEP_ALIVE;
    }
}
