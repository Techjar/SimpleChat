/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 30, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.util;

public class MathHelper {
    public MathHelper() {
    }
    
    public int clamp(int i, int low, int high) {
        return Math.max(Math.min(i, high), low);
    }

    public long clamp(long i, long low, long high) {
        return Math.max(Math.min(i, high), low);
    }

    public double clamp(double i, double low, double high) {
        return Math.max(Math.min(i, high), low);
    }

    public float clamp(float i, float low, float high) {
        return Math.max(Math.min(i, high), low);
    }
}
