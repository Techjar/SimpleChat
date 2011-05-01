/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

public class Client {
    String ip;
    int port;
    NameData name;


    public Client(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = new NameData(name);
    }

    public void start() {
        
    }
}
