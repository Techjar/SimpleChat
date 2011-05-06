/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Log formatter for the client console.
 * @author Techjar
 */
public class ClientLogFormatter extends Formatter {
    private final SimpleDateFormat date;


    /**
     * Creates a new instance of the log formatter used for the client console.
     */
    public ClientLogFormatter() {
        this.date = new SimpleDateFormat("hh:mm a");
    }

    /**
     * Formats the log record specified.
     *
     * @param record log record to format
     * @return formatted string
     */
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
