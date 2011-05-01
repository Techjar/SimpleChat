/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.io.*;
import java.util.List;

public class DataManager {
    private List ops;
    private List bans;
    private List ipbans;


    public DataManager() {
        this.loadOps();
        this.loadBans();
        this.loadIPBans();
    }

    private void loadOps() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ops.txt"));
            String line = "";
            while((line = br.readLine()) != null) ops.add(line.trim().toLowerCase());
            br.close();
        }
        catch(Exception e) {
            System.out.println("Failed to read ops.txt data.");
            e.printStackTrace();
        }
    }

    private void saveOps() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("ops.txt", false));
            for(int i = 0; i < ops.size(); i++) pw.println(ops.get(i));
            pw.close();
        }
        catch(Exception e) {
            System.out.println("Failed to save ops.txt data.");
            e.printStackTrace();
        }
    }

    private void loadBans() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("bans.txt"));
            String line = "";
            while((line = br.readLine()) != null) bans.add(line.trim().toLowerCase());
            br.close();
        }
        catch(Exception e) {
            System.out.println("Failed to read bans.txt data.");
            e.printStackTrace();
        }
    }

    private void saveBans() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("bans.txt", false));
            for(int i = 0; i < bans.size(); i++) pw.println(bans.get(i));
            pw.close();
        }
        catch(Exception e) {
            System.out.println("Failed to save bans.txt data.");
            e.printStackTrace();
        }
    }

    private void loadIPBans() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ipbans.txt"));
            String line = "";
            while((line = br.readLine()) != null) ipbans.add(line.trim().toLowerCase());
            br.close();
        }
        catch(Exception e) {
            System.out.println("Failed to read ipbans.txt data.");
            e.printStackTrace();
        }
    }

    private void saveIPBans() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("ipbans.txt", false));
            for(int i = 0; i < ipbans.size(); i++) pw.println(ipbans.get(i));
            pw.close();
        }
        catch(Exception e) {
            System.out.println("Failed to save ipbans.txt data.");
            e.printStackTrace();
        }
    }


    public boolean isOp(String name) {
        return ops.contains(name.trim().toLowerCase());
    }

    public void addOp(String name) {
        ops.add(name.trim().toLowerCase());
        this.saveOps();
    }

    public void removeOp(String name) {
        ops.remove(name.trim().toLowerCase());
        this.saveOps();
    }

    public boolean isBanned(String name) {
        return bans.contains(name.trim().toLowerCase());
    }

    public void addBan(String name) {
        bans.add(name.trim().toLowerCase());
        this.saveBans();
    }

    public void removeBan(String name) {
        bans.remove(name.trim().toLowerCase());
        this.saveBans();
    }

    public boolean isIPBanned(String ip) {
        return ipbans.contains(ip.trim().toLowerCase());
    }

    public void addIPBan(String ip) {
        ipbans.add(ip.trim().toLowerCase());
        this.saveIPBans();
    }

    public void removeIPBan(String ip) {
        ipbans.remove(ip.trim().toLowerCase());
        this.saveIPBans();
    }
}
