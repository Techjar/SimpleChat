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


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import com.simplechat.protocol.*;

public class CommandHandler {
    private ClientData client;
    private List clients;
    private DatagramSocket socket;


    public CommandHandler(ClientData client, List clients, DatagramSocket socket) {
        this.client = client;
        this.clients = clients;
        this.socket = socket;
    }

    public void parseCommand(String cmd, String[] args) {
        PacketHandler ph = new PacketHandler();
        DataManager dm = new DataManager();
        Map<String, String> cfg = new ConfigManager().load();

        if(cmd.equalsIgnoreCase("help")) {
            ph.sendPacket(new Packet5Message("/quit [message] - Disconnects you from the server."), client, this.socket);
            ph.sendPacket(new Packet5Message("/stop - Stops the server. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/say <message> - Broadcasts a server message."), client, this.socket);
            ph.sendPacket(new Packet5Message("/ping - Ping! Pong!"), client, this.socket);
            ph.sendPacket(new Packet5Message("/kill <name> - Kills a user."), client, this.socket);
            ph.sendPacket(new Packet5Message("/nuke - NUKE THE CHAT!!!!! (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/whois <name> - Gets information on a user."), client, this.socket);
            ph.sendPacket(new Packet5Message("/list - Lists users in the chat."), client, this.socket);
            ph.sendPacket(new Packet5Message("/me <message> - Makes you do an action."), client, this.socket);
            ph.sendPacket(new Packet5Message("/nick <name> [password] - Changes your name! Password may be required."), client, this.socket);
            ph.sendPacket(new Packet5Message("/password <set|remove> [password] - Allows you to set or remove your password."), client, this.socket);
            ph.sendPacket(new Packet5Message("/op <name> - Ops a user. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/deop <name> - De-ops a user. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/kick <name> - Kicks a user. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/ban <name> - Bans a user. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/unban <name> - Unbans a user. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/banip <ip> - Bans an IP. (Op-only)"), client, this.socket);
            ph.sendPacket(new Packet5Message("/unbanip <ip> - Unbans an IP. (Op-only)"), client, this.socket);
        }
        else if(cmd.equalsIgnoreCase("quit")) {
            String msg = "";
            for(int i = 0; i < args.length; i++) msg += args[i] + " ";
            msg = msg.trim();

            System.out.println(client.getUsername() + " quit. Reason: " + msg) ;
            Packet4Kick packet = new Packet4Kick("Quitting. Reason: " + msg);
            Packet5Message packet2 = new Packet5Message(client.getUsername() + " quit. (" + msg + ")");
            ph.sendPacket(packet, client, this.socket);
            clients.remove(client);
            client.stopKeepAliveThread();
            client.stopKeepAliveSendThread();
            ph.sendAllPacket(packet2, clients, this.socket);
        }
        else if(cmd.equalsIgnoreCase("stop")) {
            if(dm.isOp(client.getUsername())) {
                System.out.println(client.getUsername() + " stopped the server.");
                System.exit(0);
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("say")) {
            if(dm.isOp(client.getUsername())) {
                String msg = "";
                for(int i = 0; i < args.length; i++) msg += args[i] + " ";
                msg = msg.trim();

                Packet5Message packet = new Packet5Message("[Server] " + msg);
                ph.sendAllPacket(packet, clients, this.socket);
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("ping")) {
            Packet5Message packet = new Packet5Message("Pong!");
            ph.sendPacket(packet, client, this.socket);
        }
        else if(cmd.equalsIgnoreCase("kill")) {
            if(args.length < 1) {
                Packet5Message packet = new Packet5Message("Not enough paramters.");
                ph.sendPacket(packet, client, this.socket);
            }
            else {
                Packet5Message packet = new Packet5Message(client.getUsername() + " was kicked for killing " + args[0] + ". " + args[0] + " will be missed. :(");
                Packet4Kick packet2 = new Packet4Kick("YOU MURDERER, YOU KILLED " + args[0].toUpperCase() + "! GET OUT!!!!!");
                ph.sendAllExcludePacket(packet, clients, client, this.socket);
                ph.sendPacket(packet2, client, this.socket);
                clients.remove(client);
                client.stopKeepAliveThread();
                client.stopKeepAliveSendThread();
            }
        }
        else if(cmd.equalsIgnoreCase("nuke")) {
            if(!dm.isOp(client.getUsername())) {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
            else if(!Boolean.parseBoolean(cfg.get("enable-nuke"))) {
                Packet5Message packet = new Packet5Message("Nuke command is disabled");
                ph.sendPacket(packet, client, this.socket);
            }
            else {
                try {
                    System.out.println("Nuke started!");
                    Packet5Message packet = new Packet5Message("IT'S NUKE TIME OH BOY!!!!!");
                    ph.sendAllPacket(packet, clients, this.socket);
                    packet = new Packet5Message("NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE NUKE");
                    for(int i = 0; i < 1000; i++) {
                        if((i % 100) == 0) System.out.println("Packets left: " + (1000 - i));
                        ph.sendAllPacket(packet, clients, this.socket);
                        Thread.sleep(10);
                    }
                    System.out.println("Nuke ended!");
                    packet = new Packet5Message("Phew, now that the nuke is over, continue chatting!");
                    ph.sendAllPacket(packet, clients, this.socket);
                }
                catch(InterruptedException e) {
                    System.out.println("Nuke command thread was interrupted.");
                }
            }
        }
        else if(cmd.equalsIgnoreCase("whois")) {
            if(args.length < 1) {
                Packet5Message packet = new Packet5Message("Not enough paramters.");
                ph.sendPacket(packet, client, this.socket);
            }
            else {
                ClientData client2 = findClient(args[0]);
                if(client2 != null) {
                    String msg = "IP: " + client2.getIP().getHostAddress() + "\n";
                    msg += "Port: " + client2.getPort() + "\n";
                    msg += "Hostname: " + client2.getIP().getCanonicalHostName();
                    Packet5Message packet = new Packet5Message(msg);
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    Packet5Message packet = new Packet5Message("User not found.");
                    ph.sendPacket(packet, client, this.socket);
                }
            }
        }
        else if(cmd.equalsIgnoreCase("list")) {
            String msg = "";
            int i = 0;
            for(i = 0; i < clients.size(); i++) {
                ClientData client2 = (ClientData)clients.get(i);
                msg += client2.getUsername() + ", ";
            }

            Packet5Message packet = new Packet5Message("Online Users (" + i + "): " + msg.substring(0, msg.length() - 2));
            ph.sendPacket(packet, client, this.socket);
        }
        else if(cmd.equalsIgnoreCase("me")) {
            String msg = "";
            for(int i = 0; i < args.length; i++) msg += args[i] + " ";
            msg = msg.trim();

            System.out.println(client.getUsername() + " did action: " + msg);
            Packet5Message packet = new Packet5Message("*" + client.getUsername() + " " + msg);
            ph.sendAllPacket(packet, clients, this.socket);
        }
        else if(cmd.equalsIgnoreCase("nick")) {
            if(args.length < 1) {
                Packet5Message packet = new Packet5Message("Not enough paramters.");
                ph.sendPacket(packet, client, this.socket);
            }
            else if(args[0].equalsIgnoreCase(client.getUsername())) {
                Packet5Message packet = new Packet5Message("Your name is already " + args[0] + ".");
                ph.sendPacket(packet, client, this.socket);
            }
            else if(nameTaken(args[0]))  {
                Packet5Message packet = new Packet5Message("That name is taken.");
                ph.sendPacket(packet, client, this.socket);
            }
            else if(dm.isOp(args[0]) && ((!dm.isOp(client.getUsername()) && Boolean.parseBoolean(cfg.get("ops-login")) && args.length < 2) || (!dm.isOp(client.getUsername()) && args.length < 2))) {
                Packet5Message packet = new Packet5Message("You can't /nick to an op's name if you aren't an op or don't have the password.");
                ph.sendPacket(packet, client, this.socket);
            }
            else {
                if(dm.getUser(args[0]) != null && !dm.getUser(args[0]).equalsIgnoreCase("")) {
                    if(args.length < 2) {
                        Packet5Message packet = new Packet5Message("The password was invalid.");
                        ph.sendPacket(packet, client, this.socket);
                        return;
                    }
                    
                    if(dm.checkUser(args[0], args[1])) {
                        Packet8PasswordChange packet3 = new Packet8PasswordChange(args[1]);
                        ph.sendPacket(packet3, client, this.socket);
                    }
                    else {
                        Packet5Message packet = new Packet5Message("The password was invalid.");
                        ph.sendPacket(packet, client, this.socket);
                        return;
                    }
                }
                System.out.println(client.getUsername() + " has changed name to " + args[0]);
                Packet5Message packet = new Packet5Message(client.getUsername() + " is now known as " + args[0]);
                clients.remove(client);
                client.setUsername(args[0]);
                clients.add(client);
                Packet6NameChange packet2 = new Packet6NameChange(args[0]);
                ph.sendPacket(packet2, client, this.socket);
                try{Thread.sleep(50);}
                catch(InterruptedException e){}
                ph.sendAllPacket(packet, clients, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("password")) {
            if(args.length < 1) {
                Packet5Message packet = new Packet5Message("Not enough paramters.");
                ph.sendPacket(packet, client, this.socket);
            }
            else if(args[0].equalsIgnoreCase("set")) {
                if(args.length < 2) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    client.setPass(args[1]);
                    dm.setUser(client.getUsername(), args[1]);
                    Packet8PasswordChange packet2 = new Packet8PasswordChange(args[1]);
                    ph.sendPacket(packet2, client, this.socket);
                    Packet5Message packet = new Packet5Message("Your password has been set.");
                    ph.sendPacket(packet, client, this.socket);
                }
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                client.setPass("");
                dm.removeUser(client.getUsername());
                Packet8PasswordChange packet2 = new Packet8PasswordChange(" ");
                ph.sendPacket(packet2, client, this.socket);
                Packet5Message packet = new Packet5Message("Your password has been removed.");
                ph.sendPacket(packet, client, this.socket);
            }
            else {
                Packet5Message packet = new Packet5Message("Unknown mode, please specify either set or remove.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("op")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(args[0].equalsIgnoreCase(client.getUsername())) {
                    Packet5Message packet = new Packet5Message("You can not op yourself.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(dm.isOp(args[0])) {
                    Packet5Message packet = new Packet5Message("That user is already an op.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    dm.addOp(args[0]);
                    System.out.println(client.getUsername() + " opped " + args[0] + ".");
                    Packet5Message packet = new Packet5Message(args[0] + " has been opped.");
                    Packet5Message packet2 = new Packet5Message("You are now an op!");
                    ph.sendPacket(packet, client, this.socket);
                    ClientData client2 = findClient(args[0]);
                    if(client2 != null) ph.sendPacket(packet2, client2, this.socket);
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("deop")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(args[0].equalsIgnoreCase(client.getUsername())) {
                    Packet5Message packet = new Packet5Message("You can not deop yourself.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(!dm.isOp(args[0])) {
                    Packet5Message packet = new Packet5Message("That user is not an op.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    dm.removeOp(args[0]);
                    System.out.println(client.getUsername() + " deopped " + args[0] + ".");
                    Packet5Message packet = new Packet5Message(args[0] + " has been deopped.");
                    Packet5Message packet2 = new Packet5Message("You are no longer an op!");
                    ph.sendPacket(packet, client, this.socket);
                    ClientData client2 = findClient(args[0]);
                    if(client2 != null) ph.sendPacket(packet2, client2, this.socket);
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("kick")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(args[0].equalsIgnoreCase(client.getUsername())) {
                    Packet5Message packet = new Packet5Message("You can not kick yourself.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(findClient(args[0]) == null) {
                    Packet5Message packet = new Packet5Message("That user isn't in the chat.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    String msg = "";
                    for(int i = 1; i < args.length; i++) msg += args[i] + " ";
                    msg = msg.trim();
                    
                    System.out.println(client.getUsername() + " kicked " + args[0] + " with reason: " + msg);
                    Packet5Message packet = new Packet5Message(args[0] + " has been kicked. (" + (msg.equals("") ? "No reason." : msg) + ")");
                    Packet4Kick packet2 = new Packet4Kick("You were kicked: " + (msg.equals("") ? "No reason." : msg));
                    ClientData client2 = findClient(args[0]);
                    ph.sendPacket(packet2, client2, this.socket);
                    clients.remove(client2);
                    client2.stopKeepAliveThread();
                    client2.stopKeepAliveSendThread();
                    ph.sendAllPacket(packet, clients, this.socket);
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("ban")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(args[0].equalsIgnoreCase(client.getUsername())) {
                    Packet5Message packet = new Packet5Message("You can not ban yourself.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(dm.isBanned(args[0])) {
                    Packet5Message packet = new Packet5Message("That user is already banned.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    String msg = "";
                    for(int i = 1; i < args.length; i++) msg += args[i] + " ";
                    msg = msg.trim();

                    dm.addBan(args[0]);
                    System.out.println(client.getUsername() + " banned " + args[0] + " with reason: " + msg);
                    Packet5Message packet = new Packet5Message(args[0] + " has been banned. (" + (msg.equals("") ? "No reason." : msg) + ")");
                    Packet4Kick packet2 = new Packet4Kick("You have been banned: " + (msg.equals("") ? "No reason." : msg));
                    ClientData client2 = findClient(args[0]);
                    if(client2 != null) {
                        ph.sendPacket(packet2, client2, this.socket);
                        clients.remove(client2);
                        client2.stopKeepAliveThread();
                        client2.stopKeepAliveSendThread();
                    }
                    ph.sendAllPacket(packet, clients, this.socket);
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("unban")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(args[0].equalsIgnoreCase(client.getUsername())) {
                    Packet5Message packet = new Packet5Message("You can not unban yourself.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else if(!dm.isBanned(args[0])) {
                    Packet5Message packet = new Packet5Message("That user is not banned.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    dm.removeBan(args[0]);
                    System.out.println(client.getUsername() + " unbanned " + args[0] + ".");
                    Packet5Message packet = new Packet5Message(args[0] + " has been unbanned.");
                    ph.sendPacket(packet, client, this.socket);
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("banip")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    InetAddress ip = null;
                    try {
                        ip = InetAddress.getByName(args[0]);
                    }
                    catch(UnknownHostException e) {
                        //System.err.println("An invalid IP was entered.");
                    }

                    if(ip == null) {
                        Packet5Message packet = new Packet5Message("The IP is invalid.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else if(ip == client.getIP()) {
                        Packet5Message packet = new Packet5Message("You can not ban your own IP.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else if(dm.isIPBanned(ip.getHostAddress())) {
                        Packet5Message packet = new Packet5Message("That IP is already banned.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else {
                        String msg = "";
                        for(int i = 1; i < args.length; i++) msg += args[i] + " ";
                        msg = msg.trim();
                        
                        dm.addIPBan(ip.getHostAddress());
                        System.out.println(client.getUsername() + " banned the IP " + ip.getHostAddress() + " with reason: " + msg);
                        Packet5Message packet = new Packet5Message("The IP " + ip.getHostAddress() + " has been banned. (" + (msg.equals("") ? "No reason." : msg) + ")");
                        Packet4Kick packet2 = new Packet4Kick("Your IP has been banned: " + (msg.equals("") ? "No reason." : msg));
                        ClientData client2 = findClient(ip);
                        if(client2 != null) {
                            ph.sendPacket(packet2, client2, this.socket);
                            clients.remove(client2);
                            client2.stopKeepAliveThread();
                            client2.stopKeepAliveSendThread();
                        }
                        ph.sendAllPacket(packet, clients, this.socket);
                    }
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }
        else if(cmd.equalsIgnoreCase("unbanip")) {
            if(dm.isOp(client.getUsername())) {
                if(args.length < 1) {
                    Packet5Message packet = new Packet5Message("Not enough paramters.");
                    ph.sendPacket(packet, client, this.socket);
                }
                else {
                    InetAddress ip = null;
                    try {
                        ip = InetAddress.getByName(args[0]);
                    }
                    catch(UnknownHostException e) {
                        //System.err.println("An invalid IP was entered.");
                    }

                    if(ip == null) {
                        Packet5Message packet = new Packet5Message("The IP is invalid.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else if(ip == client.getIP()) {
                        Packet5Message packet = new Packet5Message("You can not unban your own IP.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else if(!dm.isIPBanned(ip.getHostAddress())) {
                        Packet5Message packet = new Packet5Message("That IP is not banned.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                    else {
                        dm.removeIPBan(ip.getHostAddress());
                        System.out.println(client.getUsername() + " unbanned the IP " + ip.getHostAddress() + ".");
                        Packet5Message packet = new Packet5Message("The IP " + ip.getHostAddress() + " has been unbanned.");
                        ph.sendPacket(packet, client, this.socket);
                    }
                }
            }
            else {
                Packet5Message packet = new Packet5Message("You are not an op.");
                ph.sendPacket(packet, client, this.socket);
            }
        }

        else {
            System.out.println("Command \"" + cmd + "\" not found.");
            Packet5Message packet = new Packet5Message("Unknown command.");
            ph.sendPacket(packet, client, this.socket);
        }
    }

    private boolean nameTaken(String name) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client2 = (ClientData)clients.get(i);
            if(client2.getUsername().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private ClientData findClient(String name) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client2 = (ClientData)clients.get(i);
            if(client2.getUsername().equalsIgnoreCase(name)) return client2;
        }
        return null;
    }

    private ClientData findClient(InetAddress ip) {
        for(int i = 0; i < clients.size(); i++) {
            ClientData client2 = (ClientData)clients.get(i);
            if(client2.getIP().getHostAddress().equals(ip.getHostAddress())) return client2;
        }
        return null;
    }
}
