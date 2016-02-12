package com.pddstudio.networkutils;

import android.content.Context;

import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ArpInfo;
import com.pddstudio.networkutils.utils.AdBlockUtils;
import com.pddstudio.networkutils.utils.ArpUtils;
import com.pddstudio.networkutils.utils.IpUtils;
import com.pddstudio.networkutils.utils.ScanUtils;

import java.util.List;

/**
 * This Class was created by Patrick J
 * on 11.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class NetworkUtils {

    private static NetworkUtils networkUtils;

    private final Context context;

    private NetworkUtils(Context context) {
        this.context = context;
    }

    public static NetworkUtils get(Context context) {
        if(networkUtils == null) networkUtils = new NetworkUtils(context);
        return networkUtils;
    }

    public DiscoveryService getDiscoveryService() {
        return new DiscoveryService(context);
    }

    public PingService getPingService(ProcessCallback processCallback) {
        return new PingService(processCallback);
    }

    public PortService getPortService(ProcessCallback processCallback) {
        return new PortService(processCallback);
    }

    public boolean checkDeviceUsesAdBlock() {
        return AdBlockUtils.isAdBlockActive();
    }

    public List<ArpInfo> getArpInfoList() {
        return ArpUtils.fetchArpList();
    }

    public void scanSubNet() {
        String ip = IpUtils.getCurrentIpAddress(context);
        ip = ip.substring(0, ip.lastIndexOf("."));
        try {
            new ScanUtils().scanSubnet(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
