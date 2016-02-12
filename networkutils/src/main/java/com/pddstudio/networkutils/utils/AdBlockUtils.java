package com.pddstudio.networkutils.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public final class AdBlockUtils {

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
