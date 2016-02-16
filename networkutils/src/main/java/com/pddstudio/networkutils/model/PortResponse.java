package com.pddstudio.networkutils.model;

/**
 * The result type for {@link com.pddstudio.networkutils.interfaces.ProcessCallback} calls when using {@link com.pddstudio.networkutils.PortService}
 */
public class PortResponse {

    private String ipAddress;
    private int port;
    private String message;
    private boolean portOpen;

    public PortResponse() {}

    /**
     * Returns the IP-Address of the target.
     * @return The IP-Address of the target.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the Port where the {@link com.pddstudio.networkutils.PortService} was trying to connect to.
     * @return The Port where the {@link com.pddstudio.networkutils.PortService} was trying to connect to.
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns an additional message (usually this is only not null in case an error occurred).
     * @return An additional message.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns whether the target port is opened or not.
     * @return true if port is open, false if not.
     */
    public boolean isPortOpen() {
        return portOpen;
    }

    public void setPortOpen(boolean portOpen) {
        this.portOpen = portOpen;
    }
}
