/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

import java.io.IOException;
import jline.ConsoleReader;
import com.simplechat.protocol.*;

/**
 * This thread handles client console input for chatting.
 * @author Techjar
 */
public class ConsoleInputThread extends Thread{
    private ServerData server;
    private NameData name;
    private ConsoleReader cr;


    /**
     * Creates a new instance of the client console input handler thread.
     *
     * @param server information about the server
     * @param name information about the client
     * @param cr ConsoleReader passed down from Client instance
     */
    public ConsoleInputThread(ServerData server, NameData name, ConsoleReader cr) {
        this.server = server;
        this.name = name;
        this.cr = cr;
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        try {
            PacketHandler ph = new PacketHandler();
            String line;

            while((line = cr.readLine(this.name.getName() + ": ", null).trim()) != null) {
                if(!line.isEmpty()) {
                    try {
                        if(line.length() > Short.MAX_VALUE) {
                            String line2 = "";
                            int linePos = 0;
                            while(linePos < line.length()) {
                                line2 = line.substring(Math.min(line.length(), linePos), Math.min(line.length(), linePos + Short.MAX_VALUE));
                                ph.sendClientPacket(new Packet3Chat(name.getName(), line2), server.getIP(), server.getPort(), this.server.getSocket());
                                linePos += Short.MAX_VALUE;
                                Thread.sleep(50);
                            }
                        }
                        else {
                            ph.sendClientPacket(new Packet3Chat(name.getName(), line), server.getIP(), server.getPort(), this.server.getSocket());
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error: Message is too large.");
                    }
                }
            }
        }
        catch(IOException e) {
            System.err.println("ConsoleInputThread failed, program will terminate.");
            System.exit(0);
        }
        catch(InterruptedException e) {
            System.err.println("ConsoleInputThread failed, program will terminate.");
            System.exit(0);
        }
    }
}
