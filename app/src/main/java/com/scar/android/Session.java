package com.scar.android;

/**
 * SCAR Session for holding information about the current
 * session
 */
public class Session {
    public static MetaData meta;
    public static String password;

    public static void init(MetaData data, String pas) {
        meta = data;
        password = pas;
    }

    public static void clear() {
        meta = null;
        password = null;
    }
}