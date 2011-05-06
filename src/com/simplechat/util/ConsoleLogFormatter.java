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
 * Log formatter for the server console
 * @author Techjar
 */
public class ConsoleLogFormatter extends Formatter {
    private final SimpleDateFormat date;


    /**
     * Creates a new instance of the log formatter used for the server console.
     */
    public ConsoleLogFormatter() {
        this.date = new SimpleDateFormat("HH:mm:ss");
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

        builder.append(date.format(record.getMillis()));
        builder.append(" [");
        builder.append(record.getLevel().getLocalizedName().toUpperCase());
        builder.append("] ");
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
