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

package com.pddstudio.networkutils.enums;

/**
 * Bonjour/Avahi/Zeroconf discovery types for {@link com.pddstudio.networkutils.DiscoveryService} provided by default.
 */
public enum DiscoveryType {
    /**
     * Discovery type for a normal workstation.
     */
    WORKSTATION("_workstation._tcp."),

    /**
     * Discovery type for a Mac OS X server machine.
     */
    SERVER("_servermgr._tcp."),

    /**
     * Discovery type for Web Servers.
     */
    WEB_SERVER("_http._tcp."),

    /**
     * Discovery type for Jenkins.
     */
    JENKINS("_jenkins._tcp."),

    /**
     * Discovery type for Hudson (comes with Jenkins installation by default).
     */
    HUDSON("_hudson._tcp."),

    /**
     * Discovery type for remote VNC Servers.
     */
    VNC_REMOTE("_rfb._tcp."),

    /**
     * Discovery type for Secure Shell remote login.
     */
    SSH_SERVER("_ssh._tcp."),

    /**
     * Discovery type for remote disc management systems.
     */
    REMOTE_DISK_MANAGEMENT("_udisks-ssh._tcp."),

    /**
     * Discovery type for RTSP (Real Time Streaming Protocol) services.
     */
    RTSP("_rtsp._tcp."),

    /**
     * Discovery type for Google Chromecast devices.
     */
    GOOGLE_CAST("_googlecast._tcp."),

    /**
     * Discovery type for personal file sharing (Apple)
     */
    APPLE_TALK_FILING_PROTOCOL("_afpovertcp._tcp."),

    /**
     * Discovery type for network file systems.
     */
    NETWORK_FILE_SYSTEM("_nfs._tcp."),

    /**
     * Discovery type for WebDAV servers.
     */
    WEB_DAV_FILE_SYSTEM("_webdav._tcp."),

    /**
     * Discovery type for FTP (File Transfer Protocol) servers.
     */
    FTP_SERVER("_ftp._tcp."),

    /**
     * Discovery type used by Remote AppleEvents.
     */
    REMOTE_APPLE_EVENTS("_eppc._tcp."),

    /**
     * Discovery type for telnet remote login.
     */
    TELNET("_telnet._tcp."),

    /**
     * Discovery type for Print Center (LPR).
     */
    LINE_PRINTER_DAEMON("_printer._tcp."),

    /**
     * Discovery type for Print Center (IPP).
     */
    INTERNET_PRINTING_PROTOCOL("_ipp._tcp."),

    /**
     * Discovery type for Print Center (PDL)
     */
    PDL_DATA_STREAM("_pdl-datastream._tcp."),

    /**
     * Discovery type for the AirPort Extreme Base Station to share USB printers.
     */
    REMOTE_IO_PRINTER_PROTOCOL("_riousbprint._tcp."),

    /**
     * Discovery type for iTunes Music Sharing services.
     */
    DIGITAL_AUDIO_ACCESS_PROTOCOL("_daap._tcp."),

    /**
     * Discovery type for iPhoto Photo Sharing services.
     */
    DIGITAL_PHOTO_ACCESS_PROTOCOL("_dpap._tcp."),

    /**
     * Discovery type for iChat AV services.
     */
    I_CHAT_INSTANT_MESSAGING_PROTOCOL("_presence._tcp."),

    /**
     * Discovery type for the Image Capture application (Mac) to share cameras.
     */
    IMAGE_CAPTURE_SHARING("_ica-networking._tcp."),

    /**
     * Discovery type for the AirPort Admin Utility.
     */
    AIRPORT_BASE_STATION("_airport._tcp."),

    /**
     * Discovery type for Xserve RAID Admin Utility to detect Xserve RAID hardware.
     */
    XSERVE_RAID("_xserveraid._tcp."),

    /**
     * Discovery type for Xcode's distributed builds feature.
     */
    DISTRIBUTED_COMPILER("_distcc._tcp."),

    /**
     * Discovery type for Open Directory Password Server services.
     */
    APPLE_PASSWORD_SERVER("_apple-sasl._tcp."),

    /**
     * Discovery type for AirTunes services.
     */
    REMOTE_AUDIO_OUTPUT_PROTOCOL("_raop._tcp.");

    private final String PROTOCOL_TYPE;

    DiscoveryType(String PROTOCOL_TYPE) {
        this.PROTOCOL_TYPE = PROTOCOL_TYPE;
    }

    public String getProtocolType() {
        return PROTOCOL_TYPE;
    }

}
