/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.simplechat.util.MD5Helper;

/**
 * Data manager, handles ops/bans and such.
 * @author Techjar
 */
public class DataManager {
    private List ops = new ArrayList();
    private List bans = new ArrayList();
    private List ipbans = new ArrayList();
    private Map<String, String> users = new HashMap<String, String>();


    /**
     * Creates a new instance of the server data manager.
     */
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


    /**
     * Checks if a user is an op.
     *
     * @param name user to check
     * @return weather or not user is an op
     */
    public boolean isOp(String name) {
        return ops.contains(name.trim().toLowerCase());
    }

    /**
     * Adds an op to the server.
     *
     * @param name user to add
     */
    public void addOp(String name) {
        ops.add(name.trim().toLowerCase());
        this.saveOps();
    }

    /**
     * Removes an op from the server.
     *
     * @param name user to remove
     */
    public void removeOp(String name) {
        ops.remove(name.trim().toLowerCase());
        this.saveOps();
    }

    /**
     * Check is a user is banned.
     *
     * @param name user to check
     * @return weather or not user is banned
     */
    public boolean isBanned(String name) {
        return bans.contains(name.trim().toLowerCase());
    }

    /**
     * Adds a user to the ban list.
     *
     * @param name user to add
     */
    public void addBan(String name) {
        bans.add(name.trim().toLowerCase());
        this.saveBans();
    }

    /**
     * Removes a user from the ban list.
     *
     * @param name user to remove
     */
    public void removeBan(String name) {
        bans.remove(name.trim().toLowerCase());
        this.saveBans();
    }

    /**
     * Checks if an IP address is banned.
     *
     * @param ip IP address to check
     * @return weather or not IP address is banned
     */
    public boolean isIPBanned(String ip) {
        return ipbans.contains(ip.trim().toLowerCase());
    }

    /**
     * Adds an IP address to the ban list.
     *
     * @param ip IP address to add
     */
    public void addIPBan(String ip) {
        ipbans.add(ip.trim().toLowerCase());
        this.saveIPBans();
    }

    /**
     * Removes an IP address from the ban list.
     *
     * @param ip IP address to remove
     */
    public void removeIPBan(String ip) {
        ipbans.remove(ip.trim().toLowerCase());
        this.saveIPBans();
    }

    /**
     * Gets a user's password checksum.
     *
     * @param user user to get password of
     * @return the password checksum
     */
    public String getUser(String user) {
        return users.get(user.trim().toLowerCase());
    }

    /**
     * Sets a user's password. Converts to an MD5 checksum.
     *
     * @param user user to password for
     * @param pass password to set
     */
    public void setUser(String user, String pass) {
        users.remove(user.trim().toLowerCase());
        users.put(user.trim().toLowerCase(), new MD5Helper().md5Checksum(pass));
        this.saveUsers();
    }

    /**
     * Compares the specified password with the one stored. Converts to an MD5 checksum.
     *
     * @param user user to check
     * @param pass password to compare
     * @return weather or not the passwords match
     */
    public boolean checkUser(String user, String pass) {
        return new MD5Helper().md5Checksum(pass).equalsIgnoreCase(users.get(user.trim().toLowerCase())) || users.get(user.trim().toLowerCase()) == null || users.get(user.trim().toLowerCase()).equalsIgnoreCase("");
    }

    /**
     * Adds a user's password to the user list.
     *
     * @param user user to add
     * @param pass password to add
     */
    public void addUser(String user, String pass) {
        users.put(user.trim().toLowerCase(), new MD5Helper().md5Checksum(pass));
        this.saveUsers();
    }

    /**
     * Removes a user's password from the user list.
     *
     * @param user user to remove
     */
    public void removeUser(String user) {
        users.remove(user.trim().toLowerCase());
        this.saveUsers();
    }
}
