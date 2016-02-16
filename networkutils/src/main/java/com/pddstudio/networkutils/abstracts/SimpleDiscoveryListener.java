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
