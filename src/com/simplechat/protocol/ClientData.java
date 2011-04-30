/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version
 */


package com.simplechat.protocol;

import java.net.InetAddress;
import java.util.List;
import com.simplechat.server.ClientKeepAliveThread;

public class ClientData {
    private String name;
    private InetAddress ip;
    private int port;
    private boolean activeState;
    private ClientKeepAliveThread keepAlive;

    public ClientData(String name, InetAddress ip, int port, boolean activeState) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.activeState = activeState;
    }

    public String getUsername() {
        return name;
    }

    public InetAddress getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean getActiveState() {
        return activeState;
    }

    public void startKeepAliveThread(List clients) {
        if(keepAlive.isAlive()) keepAlive.interrupt();
        this.keepAlive = new ClientKeepAliveThread(this.name, this, clients);
        keepAlive.start();
    }
}