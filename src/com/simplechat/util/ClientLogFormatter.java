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


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ClientLogFormatter extends Formatter {
    private final SimpleDateFormat date;

    public ClientLogFormatter() {
        SimpleDateFormat date = null;
        date = new SimpleDateFormat("hh:mm a");
        this.date = date;
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        Throwable ex = record.getThrown();

        builder.append("(");
        builder.append(date.format(record.getMillis()));
        builder.append(") ");
        builder.append(record.getMessage());
        builder.append('\n');

        if (ex != null) {
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            builder.append(writer);
        }

        return builder.toString();
    }

}
