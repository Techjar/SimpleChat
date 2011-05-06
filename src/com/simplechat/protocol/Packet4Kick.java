/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Kick packet, sent by server to kick a client.
 * @author Techjar
 */
public class Packet4Kick extends Packet {
    private byte[] data;
    private PacketType type;
    /**
     * Kick message.
     */
    public String msg;


    /**
     * Creates a new instance of this packet.
     *
     * @param msg kick message
     */
    public Packet4Kick(String msg) {
        byte[] msg2 = msg.substring(0, Math.min(msg.length(), Short.MAX_VALUE)).getBytes();
        this.data = new byte[msg2.length + 5];

        int i = 0;
        this.data[i++] = (byte)4;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((msg2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(msg2.length & 0xFF);
        for(int j = 0; j < msg2.length; j++) this.data[i++] = msg2[j];
        this.type = PacketType.KICK;
        this.msg = msg;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet4Kick(byte[] data) {
        int msgL = ((data[3] << 8) | (data[4] & 0xFF));
        this.data = data;
        this.type = PacketType.KICK;
        this.msg = new String(data, 5, msgL);
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
