/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 1, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import java.net.*;
import jline.ConsoleReader;
import com.simplechat.protocol.*;

public class PacketHandlerThread extends Thread {
    private DatagramPacket packet;
    private ServerData server;
    private NameData name;
    private ConsoleReader cr;
    private ConnectTimeoutThread ctt;


    public PacketHandlerThread(DatagramPacket packet, ServerData server, NameData name, ConsoleReader cr, ConnectTimeoutThread ctt) {
        this.packet = packet;
        this.server = server;
        this.name = name;
        this.cr = cr;
        this.ctt = ctt;
    }
    
    @Override
    public void run() {
        try {
            byte[] data = packet.getData();
            PacketHandler ph = new PacketHandler();
            PacketType type = ph.getPacketType(data[0]);

            //System.out.println("Recieved packet with id: " + data[0]); // DEBUG!
            if(type == PacketType.KEEP_ALIVE) {
                this.name.startKeepAliveRecieveThread();
            }
            else if(type == PacketType.KICK) {
                Packet4Kick packet2 = new Packet4Kick(data);
                System.out.println(packet2.msg);
                System.exit(0);
            }
            else if(type == PacketType.MESSAGE) {
                try {
                    Packet5Message packet2 = new Packet5Message(data);
                    String oldLine = this.name.getName() + ": ";
                    String space = "";
                    for(int i = packet2.msg.length(); i < oldLine.length(); i++) space += " ";
                    System.out.println(packet2.msg + space);
                }
                catch(StringIndexOutOfBoundsException e) {
                    String msg = "Error: Recieved message was too large.";
                    String oldLine = this.name.getName() + ": ";
                    String space = "";
                    for(int i = msg.length(); i < oldLine.length(); i++) space += " ";
                    System.out.println(msg + space);
                }
            }
            else if(type == PacketType.NAME_CHANGE) {
                Packet6NameChange packet2 = new Packet6NameChange(data);
                this.name.setName(packet2.name);
            }
            else if(type == PacketType.HANDSHAKE) {
                if(this.ctt.isAlive()) this.ctt.interrupt();
                this.name.startKeepAliveRecieveThread();
                new ClientKeepAliveThread(this.server, this.name).start();
                new ConsoleInputThread(this.server, this.name, this.cr).start();
            }
            else if(type == PacketType.PASSWORD_CHANGE) {
                Packet8PasswordChange packet2 = new Packet8PasswordChange(data);
                this.name.setPass(packet2.pass);
            }
            else {
                //System.out.println("Malformed packet recieved.");
                //System.out.println(data.toString());
            }
        }
        catch(Throwable e) {
            System.err.println("An unknown error occurred in the packet handler.");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
