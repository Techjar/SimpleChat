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

import java.io.IOException;
import jline.ConsoleReader;

public class ClientKeepAliveRecieveThread extends Thread {
    private ConsoleReader cr;


    public ClientKeepAliveRecieveThread(ConsoleReader cr) {
        this.cr = cr;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(60000);
                cr.printString(ConsoleReader.RESET_LINE + "Disconnected from server. (Ping timeout)\n");
                cr.flushConsole();
                System.exit(0);
            }
            catch(IOException e) {
                //System.err.println("ClientKeepAliveRecieveThread had an unknown I/O error.");
            }
            catch(InterruptedException e) {
                //System.err.println("ClientKeepAliveRecieveThread was interrupted.");
            }
        }
    }
}
