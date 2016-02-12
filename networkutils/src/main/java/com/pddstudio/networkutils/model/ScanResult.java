package com.pddstudio.networkutils.model;

import java.net.InetAddress;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ScanResult {

    private InetAddress inetAddress;
    private String ipAddress;
    private boolean reachable;

    public ScanResult() {}

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }
}
