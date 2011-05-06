/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Message packet, sent by server to relay a message to a client.
 * @author Techjar
 */
public class Packet5Message extends Packet {
    private byte[] data;
    private PacketType type;
    /**
     * Message to be sent.
     */
    public String msg;


    /**
     * Creates a new instance of this packet.
     *
     * @param msg message to send
     */
    public Packet5Message(String msg) {
        byte[] msg2 = msg.substring(0, Math.min(msg.length(), Short.MAX_VALUE)).getBytes();
        this.data = new byte[msg2.length + 5];

        int i = 0;
        this.data[i++] = (byte)5;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((msg2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(msg2.length & 0xFF);
        for(int j = 0; j < msg2.length; j++) this.data[i++] = msg2[j];
        this.type = PacketType.MESSAGE;
        this.msg = msg;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet5Message(byte[] data) {
        int msgL = ((data[3] << 8) | (data[4] & 0xFF));
        this.data = data;
        this.type = PacketType.MESSAGE;
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
