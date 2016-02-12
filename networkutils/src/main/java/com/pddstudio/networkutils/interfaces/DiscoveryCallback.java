package com.pddstudio.networkutils.interfaces;

import android.net.nsd.NsdServiceInfo;

/**
 * This Class was created by Patrick J
 * on 11.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public interface DiscoveryCallback {
    void onStartDiscovery();
    void onStopDiscovery();
    void onServiceFound(NsdServiceInfo nsdServiceInfo);
    void onServiceLost(NsdServiceInfo nsdServiceInfo);
    void onDiscoveryFailed(int errorCode);
}
