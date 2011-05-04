/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 3, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.util;

import java.security.MessageDigest;

public class MD5Helper {
    public MD5Helper() {
    }

    public String md5Checksum(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < digest.length; i++) sb.append(Integer.toHexString(0xFF & digest[i]));
            return sb.toString();
        }
        catch(Throwable e) {
            System.out.println("Could not generate MD5 checksum.");
            e.printStackTrace();
            return null;
        }
    }
}
