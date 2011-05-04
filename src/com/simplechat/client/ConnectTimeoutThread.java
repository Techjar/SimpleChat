/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 2, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.client;

public class ConnectTimeoutThread extends Thread {
    public ConnectTimeoutThread() {
    }

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
