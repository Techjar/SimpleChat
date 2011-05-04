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
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.simplechat.util.MD5Helper;

public class DataManager {
    private List ops = new ArrayList();
    private List bans = new ArrayList();
    private List ipbans = new ArrayList();
    private Map<String, String> users = new HashMap<String, String>();


    public DataManager() {
        this.loadOps();
        this.loadBans();
        this.loadIPBans();
        this.loadUsers();
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

    private void loadUsers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line = "";
            while((line = br.readLine()) != null) {
                String[] user = line.split(":");
                users.put(user[0], user[1]);
            }
            br.close();
        }
        catch(Exception e) {
            System.out.println("Failed to read users.txt data.");
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("users.txt", false));
            for(Map.Entry<String, String> entry : users.entrySet()) pw.println(entry.getKey() + ":" + entry.getValue());
            pw.close();
        }
        catch(Exception e) {
            System.out.println("Failed to save users.txt data.");
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

    public String getUser(String user) {
        return users.get(user.trim().toLowerCase());
    }

    public void setUser(String user, String pass) {
        users.remove(user.trim().toLowerCase());
        users.put(user.trim().toLowerCase(), new MD5Helper().md5Checksum(pass));
        this.saveUsers();
    }

    public boolean checkUser(String user, String pass) {
        return new MD5Helper().md5Checksum(pass).equalsIgnoreCase(users.get(user.trim().toLowerCase())) || users.get(user.trim().toLowerCase()) == null || users.get(user.trim().toLowerCase()).equalsIgnoreCase("");
    }

    public void addUser(String user, String pass) {
        users.put(user.trim().toLowerCase(), new MD5Helper().md5Checksum(pass));
        this.saveUsers();
    }

    public void removeUser(String user) {
        users.remove(user.trim().toLowerCase());
        this.saveUsers();
    }
}
