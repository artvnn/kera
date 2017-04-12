package com.artvnn.kera;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public class Utils {

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static String getTimeStamp () {
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).format(new Date());
    }

    public static void log (String message) {
        log(message, "");
    }

    public static void log (String message, String type) {
        System.out.printf("    {%s} %s %s -> %s%n", getThreadName(), type, getTimeStamp(), message);
    }

    public static void debug (String message) {
        log(message, "DEBUG:");
    }

    public static void error (String message, Exception ex) {
        log(message, "ERROR:");
        ex.printStackTrace();
    }

    public static String getCurrentPath () {
        return Paths.get("").toAbsolutePath().toString();
    }

    public static String readTextFile (String filenameWithPath) {
        try {
            Path file = Paths.get(filenameWithPath);
            return String.join("\n", Files.readAllLines(file));
        } catch (IOException ex) {
            error("Utils.readTextFile() " + ex.getMessage(), ex);
            return "";
        }
    }

}
