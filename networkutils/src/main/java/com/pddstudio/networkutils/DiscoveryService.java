package com.pddstudio.networkutils;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pddstudio.networkutils.enums.DiscoveryType;
import com.pddstudio.networkutils.interfaces.DiscoveryCallback;


/**
 * This Class was created by Patrick J
 * on 11.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class DiscoveryService implements NsdManager.DiscoveryListener {

    private static final String LOG_TAG = "DiscoveryService";

    private final NsdManager nsdManager;
    private DiscoveryCallback discoveryCallback;

    protected DiscoveryService(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void startDiscovery(@NonNull  String serviceType, @NonNull  DiscoveryCallback discoveryCallback) {
        this.discoveryCallback = discoveryCallback;
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, this);
        Log.d(LOG_TAG, "startDiscovery() for : " + serviceType);
    }

    public void startDiscovery(@NonNull DiscoveryType discoveryType, @NonNull DiscoveryCallback discoveryCallback) {
        startDiscovery(discoveryType.getProtocolType(), discoveryCallback);
    }

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
