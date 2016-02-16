package com.pddstudio.networkutils.model;

/**
 * The result type for {@link com.pddstudio.networkutils.interfaces.ProcessCallback} calls when using {@link com.pddstudio.networkutils.SubnetScannerService}
 */
public class ScanResult {

    private String ipAddress;
    private String hostName;
    private String canonicalHostName;
    private boolean reachable;

    public ScanResult() {}

    /**
     * Returns the target's IP-Address.
     * @return The target's IP-Address.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns whether the target was reachable or not.
     * @return true if the target was reachable, false if not.
     */
    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    /**
     * Returns the host name of the target (if any).
     * @return The host name of the target (if any).
     */
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the canonical host name (if any).
     * @return The canonical host name (if any).
     */
    public String getCanonicalHostName() {
        return canonicalHostName;
    }

    public void setCanonicalHostName(String canoncialHostName) {
        this.canonicalHostName = canoncialHostName;
    }
}
