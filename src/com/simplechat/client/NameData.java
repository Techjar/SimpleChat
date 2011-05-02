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

import jline.ConsoleReader;

public class NameData {
    private String name;
    private ConsoleReader cr;
    private ClientKeepAliveRecieveThread keepAlive;

    
    public NameData(String name, ConsoleReader cr) {
        this.name = name;
        this.cr = cr;
        this.keepAlive = new ClientKeepAliveRecieveThread(this.cr);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ConsoleReader getCr() {
        return cr;
    }

    public void setCr(ConsoleReader cr) {
        this.cr = cr;
    }

    public void startKeepAliveRecieveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
        this.keepAlive = new ClientKeepAliveRecieveThread(this.cr);
        keepAlive.start();
    }

    public void stopKeepAliveRecieveThread() {
        if(keepAlive.isAlive()) keepAlive.interrupt();
    }
}
