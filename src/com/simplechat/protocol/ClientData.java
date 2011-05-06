/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.ArrayList;
import com.simplechat.server.ClientKeepAliveThread;
import com.simplechat.server.ClientKeepAliveSendThread;

/**
 * Container for data about a client.
 * @author Techjar
 */
public class ClientData {
    private String name;
    private String pass;
    private InetAddress ip;
    private int port;
    private boolean activeState;
    private ClientKeepAliveThread keepAlive;
    private ClientKeepAliveSendThread keepAliveSend;
    private DatagramSocket socket;


    /**
     * Creates a new instance of the SimpleChat protocol client data holder.
     *
     * @param name name for this client
     * @param pass password for this client
     * @param ip IP address for this client
     * @param port port for this client
     * @param activeState weather or not this client is active
     * @param socket socket for the server this client is on
     */
    public ClientData(String name, String pass, InetAddress ip, int port, boolean activeState, DatagramSocket socket) {
        this.name = name;
        this.pass = pass;
        this.ip = ip;
        this.port = port;
        this.activeState = activeState;
        this.socket = socket;
        this.keepAlive = new ClientKeepAliveThread(this, new ArrayList(), this.socket);
        this.keepAliveSend = new ClientKeepAliveSendThread(this, this.socket);
    }

    /**
     * Retrieves the name of the client.
     *
     * @return name of client
     */
    public String getUsername() {
        return name;
    }

    /**
     * Sets the name of this client.
     *
     * @param name new name for client
     */
    public void setUsername(String name) {
        this.name = name;
    }

    /**
     * Retrieves the password for this client.
     *
     * @return password of client
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the password of this client.
     *
     * @param pass new password for client
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Retrieves the IP address for this client.
     *
     * @return the IP address
     */
    public InetAddress getIP() {
        return ip;
    }

    /**
     * Sets the IP address for this client.
     *
     * @param ip new IP address for client
     */
    public void setIP(InetAddress ip) {
        this.ip = ip;
    }

    /**
     * Retrieves the port for this client.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port for this client.
     *
     * @param port new port for client
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Retrieves weather or not this client is active.
     *
     * @return the active state
     */
    public boolean getActiveState() {
        return activeState;
    }

    /**
     * Sets weather or not this client is active.
     *
     * @param activeState new active state for client
     */
    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    /**
     * Starts or restarts the keep alive timeout thread.
     *
     * @param clients all clients on the server
     */
    public void startKeepAliveThread(List clients) {
        if(keepAlive.isAlive()) keepAlive.interrupt();
        this.keepAlive = new ClientKeepAliveThread(this, clients, this.socket);
        keepAlive.start();
    }

    /**
     * Stops the keep alive timeout thread.
     */
    public void stopKeepAliveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
    }

    /**
     * Starts or restarts the keep alive packet sender thread.
     */
    public void startKeepAliveSendThread() {
        if(keepAliveSend.isAlive()) keepAliveSend.kill();
        this.keepAliveSend = new ClientKeepAliveSendThread(this, this.socket);
        keepAliveSend.start();
    }

    /**
     * Stops the keep alive packet sender thread.
     */
    public void stopKeepAliveSendThread() {
        if(keepAliveSend.isAlive()) keepAliveSend.kill();
    }
}