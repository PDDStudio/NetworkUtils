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
/*
 * This Class was created by Patrick J
 * on 20.02.16. For more Details and licensing information
 * have a look at the README.md
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pddstudio.networkutils.enums.ConnectionType;

public class ConnectionService {

    private final ConnectivityManager connectivityManager;

    protected ConnectionService(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private ConnectionType getConnectionType(int networkType) {
        if(networkType == ConnectivityManager.TYPE_MOBILE_DUN || networkType == ConnectivityManager.TYPE_MOBILE) return ConnectionType.MOBILE;
        else if(networkType == ConnectivityManager.TYPE_ETHERNET) return ConnectionType.ETHERNET;
        else if(networkType == ConnectivityManager.TYPE_VPN) return ConnectionType.VPN;
        else if(networkType == ConnectivityManager.TYPE_WIFI) return ConnectionType.WIFI;
        else if(networkType == ConnectivityManager.TYPE_WIMAX) return ConnectionType.WIMAX;
        else return ConnectionType.UNKNOWN;
    }

    public ConnectionType getActiveConnectionType() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return getConnectionType(networkInfo.getType());
        } else return ConnectionType.UNKNOWN;
    }

}
