/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.io.*;
import java.util.logging.*;
import com.simplechat.util.LoggingOutputStream;
import com.simplechat.util.ConsoleLogFormatter;

public class Server {
    private int port;


    public Server(int port) {
        this.port = port;
    }

    public void start() {
        // Save these just in case.
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        try {
            LoggingOutputStream los;
            Handler logFile = new FileHandler("server.log", true);
            logFile.setFormatter(new ConsoleLogFormatter());
            Handler logConsole = new ConsoleHandler();
            logConsole.setFormatter(new ConsoleLogFormatter());

            Logger logger = Logger.getLogger(Server.class.getName());
            logger.setUseParentHandlers(false);
            logger.addHandler(logFile);
            logger.addHandler(logConsole);


            los = new LoggingOutputStream(logger, Level.INFO);
            System.setOut(new PrintStream(los, true));

            los = new LoggingOutputStream(logger, Level.SEVERE);
            System.setErr(new PrintStream(los, true));
        }
        catch(Throwable e) {
            System.err.println("Could not setup logger.");
            e.printStackTrace();
            System.exit(0);
        }

        try {
            new File("ops.txt").createNewFile();
            new File("bans.txt").createNewFile();
            new File("ipbans.txt").createNewFile();
        }
        catch(Throwable e) {
            System.err.println("Failed to create server data files.");
            e.printStackTrace();
        }


        new ServerListenerThread(port).start();
    }
}
