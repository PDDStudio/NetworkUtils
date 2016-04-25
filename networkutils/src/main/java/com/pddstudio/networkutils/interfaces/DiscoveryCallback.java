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

    /**
     * Gets invoked in case the {@link com.pddstudio.networkutils.DiscoveryService} could be resolved.
     * @param nsdServiceInfo
     */
    void onServiceResolved(NsdServiceInfo nsdServiceInfo);

    /**
     * Gets invoked in case the {@link com.pddstudio.networkutils.DiscoveryService} couldn't resolve the given {@link NsdServiceInfo}
     */
    void onServiceResolveFailed(NsdServiceInfo nsdServiceInfo, int errorCode);
}
