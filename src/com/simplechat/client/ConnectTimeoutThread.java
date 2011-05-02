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

import java.io.IOException;
import jline.ConsoleReader;

public class ConnectTimeoutThread extends Thread {
    private ConsoleReader cr;


    public ConnectTimeoutThread(ConsoleReader cr) {
        this.cr = cr;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            cr.printString("Could not connect to server.\n");
            cr.flushConsole();
            System.exit(0);
        }
        catch(InterruptedException e) {
            //System.out.println("ConnectTimeoutThread was interrupted.");
        }
        catch(IOException e) {
            System.out.println("Could not connect to server.");
            System.exit(0);
        }
    }
}
