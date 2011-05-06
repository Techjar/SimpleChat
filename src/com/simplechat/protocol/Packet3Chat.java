/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Chat packet, sent by client when a message is entered.
 * @author Techjar
 */
public class Packet3Chat extends Packet {
    private byte[] data;
    private PacketType type;
    /**
     * Name of the client.
     */
    public String name;
    /**
     * Message sent from client.
     */
    public String msg;


    /**
     * Creates a new instance of this packet.
     *
     * @param name name of client
     * @param msg message to send
     */
    public Packet3Chat(String name, String msg) {
        byte[] name2 = name.substring(0, Math.min(name.length(), Short.MAX_VALUE)).getBytes();
        byte[] msg2 = msg.substring(0, Math.min(msg.length(), Short.MAX_VALUE)).getBytes();
        this.data = new byte[name2.length + msg2.length + 7];

        int i = 0;
        this.data[i++] = (byte)3;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((name2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(name2.length & 0xFF);
        for(int j = 0; j < name2.length; j++) this.data[i++] = name2[j];
        this.data[i++] = (byte)((msg2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(msg2.length & 0xFF);
        for(int j = 0; j < msg2.length; j++) this.data[i++] = msg2[j];
        this.type = PacketType.CHAT;
        this.name = name;
        this.msg = msg;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet3Chat(byte[] data) {
        int nameL = ((data[3] << 8) | (data[4] & 0xFF));
        int msgL = ((data[5 + nameL] << 8) | (data[6 + nameL] & 0xFF));
        this.data = data;
        this.type = PacketType.CHAT;
        this.name = new String(data, 5, nameL);
        this.msg = new String(data, 7 + nameL, msgL);
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
