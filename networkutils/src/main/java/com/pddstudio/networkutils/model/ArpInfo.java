package com.pddstudio.networkutils.model;

/**
 * This class represents a single entry in the device's ARP-List.
 * It maps IP-Addresses and their Mac-Addresses.
 */
public class ArpInfo {

    private String ipAddress;
    private String macAddress;

    public ArpInfo() {}

    /**
     * Returns the entry's IP-Address
     * @return The entry's IP-Address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the entry's Mac-Address
     * @return The entry's Mac-Address
     */
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
