/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

import jline.ConsoleReader;

/**
 * Container for data about the client.
 * @author Techjar
 */
public class NameData {
    private String name;
    private String pass;
    private ConsoleReader cr;
    private ClientKeepAliveRecieveThread keepAlive;


    /**
     * Creates a new instance of the client data holder.
     *
     * @param name username of the client
     * @param pass password of the client
     * @param cr ConsoleReader passed down from Client instance
     */
    public NameData(String name, String pass, ConsoleReader cr) {
        this.name = name;
        this.pass = pass;
        this.cr = cr;
        this.keepAlive = new ClientKeepAliveRecieveThread();
    }

    /**
     * Retrieves the name of the client.
     *
     * @return name of client
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this client.
     *
     * @param name new name for client
     */
    public void setName(String name){
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
     * Retrieves the ConsoleReader passed down from the Client instance.
     *
     * @return the ConsoleReader for this client
     */
    public ConsoleReader getCr() {
        return cr;
    }

    /**
     * Sets the ConsoleReader for this client.
     *
     * @param cr new ConsoleReader for client
     */
    public void setCr(ConsoleReader cr) {
        this.cr = cr;
    }

    /**
     * Starts or restarts the keep alive timeout thread.
     */
    public void startKeepAliveRecieveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
        this.keepAlive = new ClientKeepAliveRecieveThread();
        keepAlive.start();
    }

    /**
     * Stops the keep alive timeout thread.
     */
    public void stopKeepAliveRecieveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
    }
}
