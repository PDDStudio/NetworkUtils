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
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, DiscoveryService.this);
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
        nsdManager.stopServiceDiscovery(DiscoveryService.this);
        Log.d(LOG_TAG, "stopDiscovery()");
    }

    public void resolveService(NsdServiceInfo nsdServiceInfo) {
        nsdManager.resolveService(nsdServiceInfo, new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.d(LOG_TAG, "onResolveFailed() : " + errorCode);
                discoveryCallback.onServiceResolveFailed(serviceInfo, errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.d(LOG_TAG, "onServiceResolved() : " + serviceInfo.getServiceName());
                discoveryCallback.onServiceResolved(serviceInfo);
            }
        });
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
