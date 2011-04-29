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

public class Server {
    private int port;


    public Server(int port) {
        this.port = port;
    }

    public void start() {
        new ServerListenerThread(port).start();
    }
}
