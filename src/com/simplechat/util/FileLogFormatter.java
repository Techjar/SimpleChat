/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Log formatter for file logging
 * @author Techjar
 */
public final class FileLogFormatter extends Formatter {
    private SimpleDateFormat date;


    /**
     * Creates date new instance of the log formatter used for file logging.
     */
    public FileLogFormatter() {
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Formats the log record specified.
     *
     * @param logrecord
     * @return formatted string
     */
    @Override
    public String format(LogRecord logrecord) {
        StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(this.date.format(Long.valueOf(logrecord.getMillis())));
        Level level = logrecord.getLevel();

        if (level == Level.FINEST) {
            stringbuilder.append(" [FINEST] ");
        } else if (level == Level.FINER) {
            stringbuilder.append(" [FINER] ");
        } else if (level == Level.FINE) {
            stringbuilder.append(" [FINE] ");
        } else if (level == Level.INFO) {
            stringbuilder.append(" [INFO] ");
        } else if (level == Level.WARNING) {
            stringbuilder.append(" [WARNING] ");
        } else if (level == Level.SEVERE) {
            stringbuilder.append(" [SEVERE] ");
        } else if (level == Level.SEVERE) {
            stringbuilder.append(" [" + level.getLocalizedName() + "] ");
        }

        stringbuilder.append(logrecord.getMessage());
        stringbuilder.append(System.getProperty("line.separator"));
        Throwable throwable = logrecord.getThrown();

        if (throwable != null) {
            StringWriter stringwriter = new StringWriter();

            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }

        return stringbuilder.toString();
    }
}
