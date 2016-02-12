package com.pddstudio.networkutils.model;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class PortResponse {

    private String ipAddress;
    private int port;
    private String message;
    private boolean portOpen;

    public PortResponse() {}

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPortOpen() {
        return portOpen;
    }

    public void setPortOpen(boolean portOpen) {
        this.portOpen = portOpen;
    }
}
