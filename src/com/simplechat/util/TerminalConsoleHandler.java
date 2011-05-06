/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import jline.ConsoleReader;

/**
 * Special console handler used for client.
 * @author Techjar
 */
public class TerminalConsoleHandler extends ConsoleHandler {
    private final ConsoleReader cr;

    /**
     * Creates a new instance of this console handler.
     * @param cr CosoleReader passed down from client instance.
     */
    public TerminalConsoleHandler(ConsoleReader cr) {
        super();
        this.cr = cr;
    }

    /**
     * Flushes this console handler.
     */
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
