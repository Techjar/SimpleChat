/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat;

import com.simplechat.client.Client;
import com.simplechat.server.Server;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("You must pass command-line arguments.");
            System.exit(0);
        }
        else if(args[0].equalsIgnoreCase("client")) {
            if(args.length < 4) {
                System.out.println("You must specify an IP, port and username.");
                System.exit(0);
            }
            else if(args.length < 5) {
                try {
                    Client client = new Client(args[1], Integer.parseInt(args[2]), args[3], "");
                    System.out.println("Connecting to server...");
                    client.start();
                }
                catch(NumberFormatException e) {
                    System.out.println("Invalid port number, using port 6000.");
                    Client client = new Client(args[1], 6000, args[3], "");
                    System.out.println("Connecting to server...");
                    client.start();
                }
            }
            else {
                try {
                    Client client = new Client(args[1], Integer.parseInt(args[2]), args[3], args[4]);
                    System.out.println("Connecting to server...");
                    client.start();
                }
                catch(NumberFormatException e) {
                    System.out.println("Invalid port number, using port 6000.");
                    Client client = new Client(args[1], 6000, args[3], args[4]);
                    System.out.println("Connecting to server...");
                    client.start();
                }
            }
        }
        else if(args[0].equalsIgnoreCase("server")) {
            if(args.length < 2) {
                System.out.println("You must specify a port.");
                System.exit(0);
            }
            try {
                Server server = new Server(Integer.parseInt(args[1]));
                server.start();
            }
            catch(NumberFormatException e) {
                System.out.println("Invalid port number, using port 6000.");
                Server server = new Server(6000);
                server.start();
            }
        }
        else {
            System.out.println("Please specify either client or server.");
            System.exit(0);
        }
    }
}
