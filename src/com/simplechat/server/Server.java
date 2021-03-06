/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.io.*;
import java.util.logging.*;
import com.simplechat.util.LoggingOutputStream;
import com.simplechat.util.FileLogFormatter;
import com.simplechat.util.ConsoleLogFormatter;

/**
 * SimpleChat server main class.
 * @author Techjar
 */
public class Server {
    private int port;


    /**
     * Creates a new instance of the SimpleChat server. Do not create more than one!
     *
     * @param port port for server to listen on
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts the processing this server instance.
     */
    public void start() {
        // Load up the config for no reason!
        new ConfigManager().load();

        // Save these just in case.
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        try {
            LoggingOutputStream los;
            Handler logFile = new FileHandler("server.log", true);
            logFile.setFormatter(new FileLogFormatter());
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
            new File("users.txt").createNewFile();
        }
        catch(Throwable e) {
            System.err.println("Failed to create server data files.");
            e.printStackTrace();
            System.exit(0);
        }


        new ServerListenerThread(port).start();
    }
}
