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

import com.pddstudio.networkutils.async.AsyncConnectionInformationTask;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ArpInfo;
import com.pddstudio.networkutils.utils.AdBlockUtils;
import com.pddstudio.networkutils.utils.ArpUtils;
import com.pddstudio.networkutils.utils.IpUtils;

import java.util.List;

/**
 * NetworkUtils Library class to interact with the library.
 */
public class NetworkUtils {

    /**
     * The static Singleton instance
     */
    private static NetworkUtils networkUtils;

    private final Context context;

    /**
     * Private {@link NetworkUtils} constructor to create a new {@link NetworkUtils} instance.
     * @param context - The Application's Context
     */
    private NetworkUtils(Context context) {
        this.context = context;
    }

    /**
     * Create a new {@link NetworkUtils} Singleton instance.
     * <i>Note: In case there is already an Singleton instance present this call will be skipped.</i>
     * @param context - The Application's Context
     */
    public static void initSingleton(Context context) {
        if(networkUtils == null) networkUtils = new NetworkUtils(context);
    }

    /**
     * Creates and returns a new {@link NetworkUtils} instance.
     * @param context - The Application's Context
     * @param overwrite - flag to create a new Instance (even if there's already an instance present)
     * @return A {@link NetworkUtils} instance.
     */
    public static NetworkUtils get(Context context, boolean overwrite) {
        if(networkUtils != null && overwrite) networkUtils = new NetworkUtils(context);
        else if(networkUtils == null) networkUtils = new NetworkUtils(context);
        return networkUtils;
    }

    /**
     * Returns the static {@link NetworkUtils} Singleton intance
     * @return {@link NetworkUtils} Singleton or null if not initialized.
     */
    public static NetworkUtils get() {
        return networkUtils;
    }

    /**
     * Returns a new {@link DiscoveryService}
     * @return A new {@link DiscoveryService}
     */
    public DiscoveryService getDiscoveryService() {
        return new DiscoveryService(context);
    }

    /**
     * Returns a new {@link PingService}
     * @param processCallback - The callback interface
     * @return A new {@link PingService}
     */
    public PingService getPingService(ProcessCallback processCallback) {
        return new PingService(processCallback);
    }

    /**
     * Returns a new {@link PortService}
     * @param processCallback - The callback interface
     * @return A new {@link PortService}
     */
    public PortService getPortService(ProcessCallback processCallback) {
        return new PortService(processCallback);
    }

    /**
     * Returns a new {@link SubnetScannerService}
     * @param processCallback - The callback interface
     * @return A new {@link SubnetScannerService}
     */
    public SubnetScannerService getSubNetScannerService(ProcessCallback processCallback) {
        return new SubnetScannerService(getSubNetAddress(), processCallback);
    }

    /**
     * Checks whether the device is blocking any kind of advertising through it's host file
     * @return true if any blocked host is detected - otherwise false
     */
    public boolean checkDeviceUsesAdBlock() {
        return AdBlockUtils.isAdBlockActive();
    }

    /**
     * Returns a List of {@link ArpInfo} instances (Mapper for IP-Address <--> Mac-Address)
     * @return a List of {@link ArpInfo} instances
     */
    public List<ArpInfo> getArpInfoList() {
        return ArpUtils.fetchArpList();
    }

    /**
     * Returns the current device's IP-Address (local network)
     * @return The current device's IP-Address
     */
    public String getCurrentIpAddress() {
        return IpUtils.getCurrentIpAddress(context);
    }

    /**
     * Returns the current Subnet Address of the current local network
     * @return The current Subnet Address of the current local network
     */
    public String getSubNetAddress() {
        String ip = IpUtils.getCurrentIpAddress(context);
        return ip.substring(0, ip.lastIndexOf("."));
    }

    /**
     * Returns a {@link com.pddstudio.networkutils.model.ConnectionInformation} instance to retrieve several information about the User's connection.
     * See the {@link com.pddstudio.networkutils.model.ConnectionInformation} class for more details.
     * @param processCallback - The callback interface
     */
    public void getConnectionInformation(ProcessCallback processCallback) {
        new AsyncConnectionInformationTask(processCallback).execute();
    }

}
