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

package com.pddstudio.networkingdemo.fragments;
/*
 * This Class was created by Patrick J
 * on 19.02.16. For more Details and licensing information
 * have a look at the README.md
 */

import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkingdemo.adapters.items.DiscoveryItem;
import com.pddstudio.networkingdemo.adapters.items.DiscoveryListItem;
import com.pddstudio.networkutils.DiscoveryService;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.enums.DiscoveryType;
import com.pddstudio.networkutils.interfaces.DiscoveryCallback;
import com.pddstudio.networkutils.interfaces.ProcessCallback;

import io.inject.InjectView;
import io.inject.Injector;

public class DiscoveryFragment extends Fragment implements DiscoveryCallback, DiscoveryListItem.OnDiscoveryAction {

    @InjectView(R.id.discoveryRecyclerView) private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter fastItemAdapter;

    private DiscoveryService discoveryService;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_discovery, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.inject(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        fastItemAdapter = new FastItemAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastItemAdapter);
        DiscoveryListItem discoveryListItem = new DiscoveryListItem(this);
        fastItemAdapter.add(discoveryListItem);
    }

    @Override
    public void onStartDiscovery(DiscoveryType discoveryType) {
        discoveryService = NetworkUtils.get(getContext(), false).getDiscoveryService();
        discoveryService.startDiscovery(discoveryType, this);
    }

    @Override
    public void onStopDiscovery() {
        discoveryService = null;
        Toast.makeText(getContext(), R.string.toast_discovery_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDiscovery() {
        Toast.makeText(getContext(), R.string.toast_discovery_start, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        DiscoveryItem discoveryItem = new DiscoveryItem(nsdServiceInfo);
        fastItemAdapter.add(discoveryItem);
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {

    }

    @Override
    public void onDiscoveryFailed(int errorCode) {
        Toast.makeText(getContext(), R.string.toast_discovery_fail, Toast.LENGTH_SHORT).show();
    }
}
