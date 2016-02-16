package com.pddstudio.networkutils.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility class for checking the device's host file an lookup Ad-blocker entries.
 */
public final class AdBlockUtils {

    /**
     * Returns whether the device has Ad blocking entries in it's host file or not.
     * @return true if Ad blocking entries where detected, false if not.
     */
    public static Boolean isAdBlockActive() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/etc/hosts")));
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) {
                if(currentLine.toLowerCase().contains("admob")) return true;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return false;
    }

}
