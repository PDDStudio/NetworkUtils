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

package com.pddstudio.networkutils.model;

/**
 * The result type for {@link com.pddstudio.networkutils.interfaces.ProcessCallback} calls when using {@link com.pddstudio.networkutils.SubnetScannerService}
 */
public class ScanResult {

    private String ipAddress;
    private String hostName;
    private String canonicalHostName;
    private boolean reachable;

    public ScanResult() {}

    /**
     * Returns the target's IP-Address.
     * @return The target's IP-Address.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns whether the target was reachable or not.
     * @return true if the target was reachable, false if not.
     */
    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    /**
     * Returns the host name of the target (if any).
     * @return The host name of the target (if any).
     */
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the canonical host name (if any).
     * @return The canonical host name (if any).
     */
    public String getCanonicalHostName() {
        return canonicalHostName;
    }

    public void setCanonicalHostName(String canoncialHostName) {
        this.canonicalHostName = canoncialHostName;
    }
}
