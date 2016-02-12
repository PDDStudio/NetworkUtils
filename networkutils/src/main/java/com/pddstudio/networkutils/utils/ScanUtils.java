package com.pddstudio.networkutils.utils;

import android.util.Log;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ScanUtils {

    public ScanUtils() {

    }

    public void scanSubnet(String subnet) throws Exception {
        List<String> subnetTargets = new LinkedList<>();

        InetAddress inetAddress;

        for(int i = 1; i < 256; i++) {
            inetAddress = InetAddress.getByName(subnet + "." + String.valueOf(i));
            Log.d("ScanUtils", "Scanning for: " + inetAddress.getHostAddress());
            if(inetAddress.isReachable(1000)) {
                Log.d("ScanUtils", "Reached target " + inetAddress.getHostName());
                subnetTargets.add(inetAddress.getHostName());
            } else {
                Log.d("ScanUtils", "Unable to reach target");
            }
        }

    }

}
