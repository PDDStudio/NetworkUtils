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

package com.pddstudio.networkutils.abstracts;

import android.net.nsd.NsdServiceInfo;

import com.pddstudio.networkutils.interfaces.DiscoveryCallback;

/**
 * A simple abstract DiscoveryListener.
 */
public abstract class SimpleDiscoveryListener implements DiscoveryCallback {

    public SimpleDiscoveryListener() {}

    public void onStartDiscovery() {}
    public void onStopDiscovery() {}

    /**
     * This method will be called as soon as the {@link com.pddstudio.networkutils.SubnetScannerService} detected one of the requested devices.
     * @param nsdServiceInfo - The {@linkplain NsdServiceInfo} instance
     */
    public abstract void onServiceFound(NsdServiceInfo nsdServiceInfo);

    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {}
    public void onDiscoveryFailed(int errorCode) {}

}
