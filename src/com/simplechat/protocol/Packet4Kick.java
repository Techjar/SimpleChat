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

public class Packet4Kick extends Packet {
    private byte[] data = new byte[512];
    private PacketType type;
    public String msg;


    public Packet4Kick(String msg) {
        byte[] msg2 = msg.getBytes();

        int i = 0;
        this.data[i++] = (byte)4;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)msg2.length;
        for(int j = 0; j < msg2.length; j++) this.data[i++] = msg2[j];
        this.type = PacketType.KICK;
        this.msg = msg;
    }

    public Packet4Kick(byte[] data) {
        this.data = data;
        this.type = PacketType.KICK;
        this.msg = new String(data, 4, data[3]);
    }
}
