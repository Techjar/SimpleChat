/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 4, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import jline.ConsoleReader;

public class TerminalConsoleHandler extends ConsoleHandler {
    private final ConsoleReader cr;

    public TerminalConsoleHandler(ConsoleReader cr) {
        super();
        this.cr = cr;
    }

    @Override
    public synchronized void flush() {
        try {
            cr.printString(ConsoleReader.RESET_LINE + "");
            cr.flushConsole();
            super.flush();
            try {
                cr.drawLine();
            }
            catch(Throwable e) {
                cr.getCursorBuffer().clearBuffer();
            }
            cr.flushConsole();
        } catch (IOException e) {
            System.err.println("An unknown I/O error occurred in the TerminalConsoleHandler.");
            e.printStackTrace();
        }
    }
}
