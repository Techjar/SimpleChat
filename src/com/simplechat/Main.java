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

public class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("You must pass command-line arguments.");
            System.exit(0);
        }
        else if(args[0].equalsIgnoreCase("client")) {
            
        }
        else if(args[0].equalsIgnoreCase("server")) {
            
        }
        else {
            System.out.println("Please specify either client or server.");
            System.exit(0);
        }
    }
}
