package com.pddstudio.networkutils.abstracts;

import android.net.nsd.NsdServiceInfo;

import com.pddstudio.networkutils.interfaces.DiscoveryCallback;

/**
 * This Class was created by Patrick J
 * on 11.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public abstract class SimpleDiscoveryListener implements DiscoveryCallback {

    public SimpleDiscoveryListener() {}

    public void onStartDiscovery() {}
    public void onStopDiscovery() {}

    public abstract void onServiceFound(NsdServiceInfo nsdServiceInfo);

    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {}
    public void onDiscoveryFailed(int errorCode) {}

}
