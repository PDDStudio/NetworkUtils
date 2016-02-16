package com.pddstudio.networkutils;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pddstudio.networkutils.enums.DiscoveryType;
import com.pddstudio.networkutils.interfaces.DiscoveryCallback;


/**
 * A Service for looking up several discovery types (Avahi/Bonjour/Zeroconf) on a network.
 */
public class DiscoveryService implements NsdManager.DiscoveryListener {

    private static final String LOG_TAG = "DiscoveryService";

    private final NsdManager nsdManager;
    private DiscoveryCallback discoveryCallback;

    protected DiscoveryService(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    /**
     * Start looking up for the provided discovery type. The results will be sent to the provided {@link DiscoveryCallback}.
     * @param serviceType - The discovery type to look for.
     * @param discoveryCallback - The callback interface.
     */
    public void startDiscovery(@NonNull  String serviceType, @NonNull  DiscoveryCallback discoveryCallback) {
        this.discoveryCallback = discoveryCallback;
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, this);
        Log.d(LOG_TAG, "startDiscovery() for : " + serviceType);
    }

    /**
     * Start looking up for the provided {@link DiscoveryType}. The results will be sent to the provided {@link DiscoveryCallback}.
     * @param discoveryType - The discovery type to look for.
     * @param discoveryCallback - The callback interface.
     */
    public void startDiscovery(@NonNull DiscoveryType discoveryType, @NonNull DiscoveryCallback discoveryCallback) {
        startDiscovery(discoveryType.getProtocolType(), discoveryCallback);
    }

    /**
     * Stops the service to lookup for network devices with the given service type.
     */
    public void stopDiscovery() {
        nsdManager.stopServiceDiscovery(this);
        Log.d(LOG_TAG, "stopDiscovery()");
    }

    @Override
    public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        Log.d(LOG_TAG, "onStartDiscoveryFailed() : " + serviceType + " error code: " + errorCode);
        discoveryCallback.onDiscoveryFailed(errorCode);
    }

    @Override
    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        Log.d(LOG_TAG, "onStopDiscoveryFailed() : " + serviceType + " error code: " + errorCode);
        discoveryCallback.onDiscoveryFailed(errorCode);
    }

    @Override
    public void onDiscoveryStarted(String serviceType) {
        Log.d(LOG_TAG, "onDiscoveryStarted() : " + serviceType);
        discoveryCallback.onStartDiscovery();
    }

    @Override
    public void onDiscoveryStopped(String serviceType) {
        Log.d(LOG_TAG, "onDiscoveryStopped() : " + serviceType);
        discoveryCallback.onStopDiscovery();
    }

    @Override
    public void onServiceFound(NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, "onServiceFound() : NAME = " + serviceInfo.getServiceName() + " TYPE = " + serviceInfo.getServiceType());
        discoveryCallback.onServiceFound(serviceInfo);
    }

    @Override
    public void onServiceLost(NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, "onServiceLost() : "  + serviceInfo);
        discoveryCallback.onServiceLost(serviceInfo);
    }
}
