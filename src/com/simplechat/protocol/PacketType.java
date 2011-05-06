/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.protocol;

/**
 * Holds packet type enums, used for checking packet type.
 * @author Techjar
 */
public enum PacketType {
    KEEP_ALIVE,
    JOIN,
    LEAVE,
    CHAT,
    KICK,
    MESSAGE,
    NAME_CHANGE,
    HANDSHAKE,
    PASSWORD_CHANGE,
    UNKNOWN
}
