/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.client;

/**
 * Thread for server connection timeout, exits client after 10 seconds unless interrupted.
 * @author Techjar
 */
public class ConnectTimeoutThread extends Thread {
    /**
     * Creates a new instance of the connection timeout thread.
     */
    public ConnectTimeoutThread() {
    }

    /**
     * Runs this thread.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            System.out.println("Could not connect to server.");
            System.exit(0);
        }
        catch(InterruptedException e) {
            //System.out.println("ConnectTimeoutThread was interrupted.");
        }
    }
}
