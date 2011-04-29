/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.net.ServerSocket;
import java.io.IOException;
import com.simplechat.protocol.*;

public class ServerListenerThread extends Thread {
    private int port;


    public ServerListenerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        boolean listen = true;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listener started on port " + port + ".");
            System.out.println("Now accepting connections from clients.");
        }
        catch (IOException e) {
            System.err.println("Could not start listener on port " + port + ".");
            System.exit(0);
        }

        try {
            while(listen) {
                
            }
            serverSocket.close();
        }
        catch (IOException e) {
            System.err.println("Could not accept client connections.");
            System.exit(0);
        }
    }
}
