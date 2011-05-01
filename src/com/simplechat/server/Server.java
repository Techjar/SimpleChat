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

import java.io.*;

public class Server {
    private int port;


    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            new File("ops.txt").createNewFile();
            new File("bans.txt").createNewFile();
            new File("ipbans.txt").createNewFile();
        }
        catch(Throwable e) {
            System.err.println("Failed to create server data files.");
            e.printStackTrace();
        }
        new ServerListenerThread(port).start();
    }
}
