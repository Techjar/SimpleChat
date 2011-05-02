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

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.ArrayList;
import com.simplechat.server.ClientKeepAliveThread;
import com.simplechat.server.ClientKeepAliveSendThread;

public class ClientData {
    private String name;
    private InetAddress ip;
    private int port;
    private boolean activeState;
    private ClientKeepAliveThread keepAlive;
    private ClientKeepAliveSendThread keepAliveSend;
    private DatagramSocket socket;

    public ClientData(String name, InetAddress ip, int port, boolean activeState, DatagramSocket socket) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.activeState = activeState;
        this.socket = socket;
        this.keepAlive = new ClientKeepAliveThread(this.name, this, new ArrayList(), this.socket);
        this.keepAliveSend = new ClientKeepAliveSendThread(this.name, this, this.socket);
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
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

    public boolean getActiveState() {
        return activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public void startKeepAliveThread(List clients) {
        if(keepAlive.isAlive()) keepAlive.interrupt();
        this.keepAlive = new ClientKeepAliveThread(this.name, this, clients, this.socket);
        keepAlive.start();
    }

    public void stopKeepAliveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
    }

    public void startKeepAliveSendThread() {
        if(keepAliveSend.isAlive()) keepAliveSend.interrupt();
        this.keepAliveSend = new ClientKeepAliveSendThread(this.name, this, this.socket);
        keepAliveSend.start();
    }

    public void stopKeepAliveSendThread() {
        if(keepAliveSend.isAlive()) keepAliveSend.interrupt();
    }
}