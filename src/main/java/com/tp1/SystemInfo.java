package com.tp1;

public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version");
    }
    public static String javaLog(){return System.getProperty("os.name");

    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}