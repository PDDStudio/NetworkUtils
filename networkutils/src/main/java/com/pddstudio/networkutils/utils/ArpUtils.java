/*
 * Copyright 2016 Patrick J
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

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
 * Utility class to retrieve information about the current ARP list.
 */
public class ArpUtils {

    /**
     * Reads the current ARP list and returns a List of {@link ArpInfo} instances.
     * @return A list of {@link ArpInfo} instances.
     */
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

    /**
     * Checks whether an IP-Address exists or not (in the ARP List)
     * @param ipAddress - The IP-Address to check for.
     * @return True if IP-Address exist, false if not.
     */
    public static boolean ipAddressExist(String ipAddress) {
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getIpAddress().toLowerCase().equals(ipAddress.toLowerCase())) return true;
        }
        return false;
    }

    /**
     * Checks whether an Mac-Adress exists or not (in the ARP List)
     * @param macAddress - The Mac-Address to check for.
     * @return True if Mac-Address exist, false if not.
     */
    public static boolean macAddressExist(String macAddress) {
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getMacAddress().toLowerCase().equals(macAddress.toLowerCase())) return true;
        }
        return false;
    }

    /**
     * Looks up the current ARP list and returns an {@link ArpInfo} instance for the given IP-Address.
     * If not matching entry was found this method will return null.
     * @param ipAddress - The IP-Address to lookup.
     * @return An {@link ArpInfo} instance with the information for the given IP-Address.
     */
    @Nullable
    public static ArpInfo getArpInfoForIpAddress(String ipAddress) {
        if(!ipAddressExist(ipAddress)) return null;
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getIpAddress().toLowerCase().equals(ipAddress.toLowerCase())) return arpInfo;
        }
        return null;
    }

    /**
     * Looks up the current ARP list and returns an {@link ArpInfo} instance for the given Mac-Address.
     * If no matching entry was found this method will return null.
     * @param macAddress - The Mac-Address to lookup.
     * @return An {@link ArpInfo} instance with the information for the given Mac-Address.
     */
    @Nullable
    public static ArpInfo getArpInfoForMacAddress(String macAddress) {
        if(!macAddressExist(macAddress)) return null;
        for(ArpInfo arpInfo : fetchArpList()) {
            if(arpInfo.getMacAddress().toLowerCase().equals(macAddress.toLowerCase())) return arpInfo;
        }
        return null;
    }

}
