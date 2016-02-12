package com.pddstudio.networkutils.enums;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public enum DiscoveryType {
    WORKSTATION("_workstation._tcp."),
    WEB_SERVER("_http._tcp."),
    JENKINS("_jenkins._tcp."),
    HUDSON("_hudson._tcp."),
    VNC_REMOTE("_rfb._tcp."),
    SSH_SERVER("_ssh._tcp."),
    REMOTE_DISK_MANAGEMENT("_udisks-ssh._tcp."),
    RTSP("_rtsp._tcp.");

    private final String PROTOCOL_TYPE;

    DiscoveryType(String PROTOCOL_TYPE) {
        this.PROTOCOL_TYPE = PROTOCOL_TYPE;
    }

    public String getProtocolType() {
        return PROTOCOL_TYPE;
    }

}
