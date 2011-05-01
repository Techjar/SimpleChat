/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date Apr 29, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.protocol;

public enum PacketType {
    KEEP_ALIVE,
    JOIN,
    LEAVE,
    CHAT,
    KICK,
    MESSAGE,
    NAME_CHANGE,
    UNKNOWN
}
