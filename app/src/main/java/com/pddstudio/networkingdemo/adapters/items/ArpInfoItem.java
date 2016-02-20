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

package com.pddstudio.networkingdemo.adapters.items;
/*
 * This Class was created by Patrick J
 * on 20.02.16. For more Details and licensing information
 * have a look at the README.md
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.pddstudio.networkingdemo.R;

public class ArpInfoItem extends AbstractItem<ArpInfoItem, ArpInfoItem.ViewHolder> {

    public final String ipAddress;
    public final String macAddress;

    public ArpInfoItem(String ipAddress, String macAddress) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_arp_info;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.ipAddressView.setText(ipAddress);
        viewHolder.macAddressView.setText(macAddress);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ipAddressView;
        TextView macAddressView;

        public ViewHolder(View itemView) {
            super(itemView);
            ipAddressView = (TextView) itemView.findViewById(R.id.ipAddressView);
            macAddressView = (TextView) itemView.findViewById(R.id.macAddressView);
        }
    }
}
