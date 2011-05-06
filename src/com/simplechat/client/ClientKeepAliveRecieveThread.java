/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

/**
 * Thread sleeps for 60 seconds then disconnects the client, unless interrupted.
 * @author Techjar
 */
public class ClientKeepAliveRecieveThread extends Thread {
    /**
     * Creates a new instance of the keep alive timeout thread.
     */
    public ClientKeepAliveRecieveThread() {
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(60000);
            System.out.println("Disconnected from server. (Ping timeout)");
            System.exit(0);
        }
        catch(InterruptedException e) {
            //System.err.println("ClientKeepAliveRecieveThread was interrupted.");
        }
    }
}
