/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerData {
    private InetAddress ip;
    private int port;
    private DatagramSocket socket;


    public ServerData(InetAddress ip, int port, DatagramSocket socket) {
        this.ip = ip;
        this.port = port;
        this.socket = socket;
    }

    public InetAddress getIP() {
        return ip;
    }

    public void setIP(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }
}
