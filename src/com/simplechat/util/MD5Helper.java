/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.util;

import java.security.MessageDigest;

/**
 * MD5 checksum helper class.
 * @author Techjar
 */
public class MD5Helper {

    /**
     * Creates a new instance of the MD5 checksum helper.
     */
    public MD5Helper() {
    }

    /**
     * Outputs an MD5 checksum from a string.
     * @param str string to convert to an MD5 checksum
     * @return the MD5 checksum
     */
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
