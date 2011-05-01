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
    
    public int clamp(int num, int min, int max) {
        if(num < min) return min;
        if(num > max) return max;
        return num;
    }
}
