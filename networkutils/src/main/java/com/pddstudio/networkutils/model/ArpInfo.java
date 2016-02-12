package com.pddstudio.networkutils.model;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ArpInfo {

    private String ipAddress;
    private String macAddress;

    public ArpInfo() {}

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
