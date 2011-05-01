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
import java.io.IOException;
import jline.ConsoleReader;
import com.simplechat.protocol.*;

public class PacketHandlerThread extends Thread {
    private DatagramPacket packet;
    private ServerData server;
    private NameData name;
    private ConsoleReader cr;


    public PacketHandlerThread(DatagramPacket packet, ServerData server, NameData name, ConsoleReader cr) {
        this.packet = packet;
        this.server = server;
        this.name = name;
        this.cr = cr;
    }

    @Override
    public void run() {
        try {
            byte[] data = packet.getData();
            PacketHandler ph = new PacketHandler();
            PacketType type = ph.getPacketType(data[0]);

            //System.out.println("Recieved packet with id: " + data[0]); // DEBUG!
            if(type == PacketType.KICK) {
                Packet4Kick packet2 = new Packet4Kick(data);
                cr.printString(ConsoleReader.RESET_LINE + packet2.msg + "\n");
                cr.flushConsole();
                System.exit(0);
            }
            else if(type == PacketType.MESSAGE) {
                Packet5Message packet2 = new Packet5Message(data);
                String[] split = this.name.getName().split("");
                String space = "";
                for(int i = 0; i < split.length; i++) space += " ";
                cr.printString(ConsoleReader.RESET_LINE + packet2.msg + space + "\n");
                cr.flushConsole();
                try {
                    cr.drawLine();
                }
                catch (Throwable e) {
                    cr.getCursorBuffer().clearBuffer();
                }
                cr.flushConsole();
            }
            else if(type == PacketType.NAME_CHANGE) {
                Packet6NameChange packet2 = new Packet6NameChange(data);
                this.name.setName(packet2.name);
            }
            else {
                //System.out.println("Malformed packet recieved.");
                //System.out.println(data.toString());
            }
        }
        catch(IOException e) {
            System.err.println("An unknown I/O error occurred in the packet handler.");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
