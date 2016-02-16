package com.pddstudio.networkutils.interfaces;

import android.net.nsd.NsdServiceInfo;

/**
 * The interface used by the {@link com.pddstudio.networkutils.DiscoveryService} to deliver callbacks.
 */
public interface DiscoveryCallback {

    /**
     * Gets invoked as soon as the {@link com.pddstudio.networkutils.DiscoveryService} started looking up for requested services.
     */
    void onStartDiscovery();

    /**
     * Gets invoked as soon as the {@link com.pddstudio.networkutils.DiscoveryService} stops looking up for requested services.
     */
    void onStopDiscovery();

    /**
     * Gets invoked as soon as the {@link com.pddstudio.networkutils.DiscoveryService} found a service for the requested lookup parameter.
     * @param nsdServiceInfo
     */
    void onServiceFound(NsdServiceInfo nsdServiceInfo);

    /**
     * Gets invoked as soon as the {@link com.pddstudio.networkutils.DiscoveryService} lost a service for the requested lookup parameter.
     * @param nsdServiceInfo
     */
    void onServiceLost(NsdServiceInfo nsdServiceInfo);

    /**
     * Gets invoked in case the  {@link com.pddstudio.networkutils.DiscoveryService} couldn't start or failed to start.
     * @param errorCode - The error code as {@linkplain Integer} value.
     */
    void onDiscoveryFailed(int errorCode);
}
