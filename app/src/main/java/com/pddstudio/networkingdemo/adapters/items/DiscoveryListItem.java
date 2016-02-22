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

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkutils.enums.DiscoveryType;

public class DiscoveryListItem extends AbstractItem<DiscoveryListItem, DiscoveryListItem.ViewHolder> {

    private DiscoveryType discoveryType;
    private boolean active = false;
    private final OnDiscoveryAction onDiscoveryAction;

    public interface OnDiscoveryAction {
        void onStartDiscovery(DiscoveryType discoveryType);
        void onStopDiscovery();
    }

    public DiscoveryListItem(OnDiscoveryAction onDiscoveryAction) {
        this.onDiscoveryAction = onDiscoveryAction;
    }

    @Override
    public int getType() {
        return R.id.discovery_list_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_discovery_list;
    }

    @Override
    public void bindView(final ViewHolder viewHolder) {
        super.bindView(viewHolder);
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewHolder.serviceSpinner.getContext(), android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.addAll(DiscoveryType.values());
        viewHolder.serviceSpinner.setAdapter(arrayAdapter);
        viewHolder.serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((AppCompatTextView) view.findViewById(android.R.id.text1)).getText().toString();
                Log.d("DiscoveryItem", "Selected position: " + position + " ID: " + id + " Text: " + text);
                discoveryType = DiscoveryType.valueOf(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewHolder.discoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active) {
                    active = false;
                    onDiscoveryAction.onStopDiscovery();
                    viewHolder.discoveryButton.setText(R.string.discovery_btn_start);
                } else {
                    active = true;
                    onDiscoveryAction.onStartDiscovery(discoveryType);
                    viewHolder.discoveryButton.setText(R.string.discovery_btn_stop);
                }
            }
        });
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        Spinner serviceSpinner;
        Button discoveryButton;

        public ViewHolder(View itemView) {
            super(itemView);
            serviceSpinner = (Spinner) itemView.findViewById(R.id.discoverySpinner);
            discoveryButton = (Button) itemView.findViewById(R.id.discoveryBtn);
        }
    }

}
