/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Password change packet, sent by server to change a client's password.
 * @author Techjar
 */
public class Packet8PasswordChange extends Packet {
    private byte[] data;
    private PacketType type;
    /**
     * New password for client.
     */
    public String pass;


    /**
     * Creates a new instance of this packet.
     *
     * @param pass new password for client
     */
    public Packet8PasswordChange(String pass) {
        byte[] pass2 = pass.substring(0, Math.min(pass.length(), Short.MAX_VALUE)).getBytes();
        this.data = new byte[pass2.length + 5];

        int i = 0;
        this.data[i++] = (byte)8;
        this.data[i++] = (byte)'S';
        this.data[i++] = (byte)'C';
        this.data[i++] = (byte)((pass2.length >> 8) & 0xFF);
        this.data[i++] = (byte)(pass2.length & 0xFF);
        for(int j = 0; j < pass2.length; j++) this.data[i++] = pass2[j];
        this.type = PacketType.PASSWORD_CHANGE;
        this.pass = pass;
    }

    /**
     * Constructs bytes back into a usable packet.
     *
     * @param data the bytes
     */
    public Packet8PasswordChange(byte[] data) {
        int passL = ((data[3] << 8) | (data[4] & 0xFF));
        this.data = data;
        this.type = PacketType.PASSWORD_CHANGE;
        this.pass = new String(data, 5, passL).trim();
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
