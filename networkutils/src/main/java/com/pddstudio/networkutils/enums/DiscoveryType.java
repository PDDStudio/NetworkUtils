package com.pddstudio.networkutils.enums;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public enum DiscoveryType {
    WORKSTATION("_workstation._tcp."),
    SERVER("_servermgr._tcp."),
    WEB_SERVER("_http._tcp."),
    JENKINS("_jenkins._tcp."),
    HUDSON("_hudson._tcp."),
    VNC_REMOTE("_rfb._tcp."),
    SSH_SERVER("_ssh._tcp."),
    REMOTE_DISK_MANAGEMENT("_udisks-ssh._tcp."),
    RTSP("_rtsp._tcp."),
    GOOGLE_CAST("_googlecast._tcp."),
    APPLE_TALK_FILING_PROTOCOL("_afpovertcp._tcp."),
    NETWORK_FILE_SYSTEM("_nfs._tcp."),
    WEB_DAV_FILE_SYSTEM("_webdav._tcp."),
    FTP_SERVER("_ftp._tcp."),
    REMOTE_APPLE_EVENTS("_eppc._tcp."),
    TELNET("_telnet._tcp."),
    LINE_PRINTER_DAEMON("_printer._tcp."),
    INTERNET_PRINTING_PROTOCOL("_ipp._tcp."),
    PDL_DATA_STREAM("_pdl-datastream._tcp."),
    REMOTE_IO_PRINTER_PROTOCOL("_riousbprint._tcp."),
    DIGITAL_AUDIO_ACCESS_PROTOCOL("_daap._tcp."),
    DIGITAL_PHOTO_ACCESS_PROTOCOL("_dpap._tcp."),
    I_CHAT_INSTANT_MESSAGING_PROTOCOL("_presence._tcp."),
    IMAGE_CAPTURE_SHARING("_ica-networking._tcp."),
    AIRPORT_BASE_STATION("_airport._tcp."),
    XSERVE_RAID("_xserveraid._tcp."),
    DISTRIBUTED_COMPILER("_distcc._tcp."),
    APPLE_PASSWORD_SERVER("_apple-sasl._tcp."),
    REMOTE_AUDIO_OUTPUT_PROTOCOL("_raop._tcp.");

    private final String PROTOCOL_TYPE;

    DiscoveryType(String PROTOCOL_TYPE) {
        this.PROTOCOL_TYPE = PROTOCOL_TYPE;
    }

    public String getProtocolType() {
        return PROTOCOL_TYPE;
    }

}
