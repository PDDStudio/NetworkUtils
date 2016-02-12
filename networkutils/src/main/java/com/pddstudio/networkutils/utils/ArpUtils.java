package com.pddstudio.networkutils.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.pddstudio.networkutils.model.ArpInfo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ArpUtils {

    public static List<ArpInfo> fetchArpList() {
        List<ArpInfo> arpInfos = new LinkedList<>();
        try {
            List<String> arpList = FileUtils.readLines(new File("/proc/net/arp"));
            for(String s : arpList) {
                Log.d("ArpUtils", "Line: " + s);
                String[] splitted = s.split(" +");
                if(splitted[0].equals("IP")) continue;
                String ip = splitted[0];
                String mac = splitted[3];
                ArpInfo arpInfo = new ArpInfo();
                arpInfo.setIpAddress(ip);
                arpInfo.setMacAddress(mac);
                arpInfos.add(arpInfo);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return arpInfos;
    }

    public static boolean ipAddressExist(String ipAddress) {
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getIpAddress().toLowerCase().equals(ipAddress.toLowerCase())) return true;
        }
        return false;
    }

    public static boolean macAddressExist(String macAddress) {
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getMacAddress().toLowerCase().equals(macAddress.toLowerCase())) return true;
        }
        return false;
    }

    @Nullable
    public static ArpInfo getArpInfoForIpAddress(String ipAddress) {
        if(!ipAddressExist(ipAddress)) return null;
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getIpAddress().toLowerCase().equals(ipAddress.toLowerCase())) return arpInfo;
        }
        return null;
    }

    @Nullable
    public static ArpInfo getArpInfoForMacAddress(String macAddress) {
        if(!macAddressExist(macAddress)) return null;
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getMacAddress().toLowerCase().equals(macAddress.toLowerCase())) return arpInfo;
        }
        return null;
    }

}
