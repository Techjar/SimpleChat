/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Container for data about the server.
 * @author Techjar
 */
public class ServerData {
    private InetAddress ip;
    private int port;
    private DatagramSocket socket;


    /**
     * Creates a new instance of the server data holder.
     *
     * @param ip IP of server
     * @param port port of server
     * @param socket socket used to connect to the server
     */
    public ServerData(InetAddress ip, int port, DatagramSocket socket) {
        this.ip = ip;
        this.port = port;
        this.socket = socket;
    }

    /**
     * Retrieves this server's IP address.
     *
     * @return the IP address
     */
    public InetAddress getIP() {
        return ip;
    }

    /**
     * Sets this server's IP address.
     *
     * @param ip new IP address for this server
     */
    public void setIP(InetAddress ip) {
        this.ip = ip;
    }

    /**
     * Retrieves this server's port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets this server's port.
     *
     * @param port new port for this server
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Retrieves this server's DatagramSocket.
     *
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * Sets this server's DatagramSocket.
     *
     * @param socket new socket for this server
     */
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }
}
