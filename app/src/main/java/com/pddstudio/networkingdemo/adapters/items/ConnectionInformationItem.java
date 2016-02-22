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
 * on 22.02.16. For more Details and licensing information
 * have a look at the README.md
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkutils.model.ConnectionInformation;

public class ConnectionInformationItem extends AbstractItem<ConnectionInformationItem, ConnectionInformationItem.ViewHolder> {

    private final ConnectionInformation connectionInformation;

    public ConnectionInformationItem(ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    @Override
    public int getType() {
        return R.id.con_info_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_con_info;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.cityTextView.setText(connectionInformation.getCity() + " (" + connectionInformation.getCountry() + " | " + connectionInformation.getCountryCode() + ")");
        viewHolder.coordinatesTextView.setText(connectionInformation.getLatitude() + " | " + connectionInformation.getLongitude());
        viewHolder.regionTextView.setText(connectionInformation.getRegionName() + " (" + connectionInformation.getRegion() + ")");
        viewHolder.timeZoneTextView.setText(connectionInformation.getTimezone());
        viewHolder.zipCodeTextView.setText(connectionInformation.getZip());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cityTextView;
        TextView coordinatesTextView;
        TextView regionTextView;
        TextView timeZoneTextView;
        TextView zipCodeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cityTextView = (TextView) itemView.findViewById(R.id.conCityView);
            coordinatesTextView = (TextView) itemView.findViewById(R.id.conCoordinatesView);
            regionTextView = (TextView) itemView.findViewById(R.id.conRegionView);
            timeZoneTextView = (TextView) itemView.findViewById(R.id.conTimeZoneView);
            zipCodeTextView = (TextView) itemView.findViewById(R.id.conZipView);
        }
    }
}
