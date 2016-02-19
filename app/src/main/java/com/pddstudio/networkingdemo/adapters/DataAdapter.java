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

package com.pddstudio.networkingdemo.adapters;
/*
 * This Class was created by Patrick J
 * on 19.02.16. For more Details and licensing information
 * have a look at the README.md
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkutils.model.ArpInfo;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    //object identifiers
    private static final int ARP_INFO_ITEM = 0;
    private static final int PORT_INFO_ITEM = 1;

    private List<?> itemData;

    public DataAdapter(List itemData) {
        this.itemData = itemData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ARP_INFO_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arp_info, parent, false);
                return new ViewHolder(view, ARP_INFO_ITEM);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemType()) {
            case ARP_INFO_ITEM:
                prepareArpInfoItem(holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        if(itemData.get(position) instanceof ArpInfo) {
            return ARP_INFO_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    private void prepareArpInfoItem(ViewHolder holder, int position) {
        ArpInfo arpInfo = (ArpInfo) itemData.get(position);
        holder.textItem1.setText(arpInfo.getIpAddress());
        holder.textItem2.setText(arpInfo.getMacAddress());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int itemType;

        TextView textItem1;
        TextView textItem2;

        public ViewHolder(View itemView, int itemType) {
            super(itemView);
            this.itemType = itemType;
            prepareLayout(itemView);
        }

        private void prepareLayout(View view) {
            switch (itemType) {
                case ARP_INFO_ITEM:
                    assignArpInfo(view);
                    break;
            }
        }

        public int getItemType() {
            return itemType;
        }

        private void assignArpInfo(View view) {
            textItem1 = (TextView) view.findViewById(R.id.dataText1);
            textItem2 = (TextView) view.findViewById(R.id.dataText2);
        }

    }

}
