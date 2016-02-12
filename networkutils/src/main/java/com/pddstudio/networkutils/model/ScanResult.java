package com.pddstudio.networkutils.model;

import java.net.InetAddress;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ScanResult {

    private String ipAddress;
    private String hostName;
    private String canonicalHostName;
    private boolean reachable;

    public ScanResult() {}

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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCanoncialHostName() {
        return canonicalHostName;
    }

    public void setCanoncialHostName(String canoncialHostName) {
        this.canonicalHostName = canoncialHostName;
    }
}
