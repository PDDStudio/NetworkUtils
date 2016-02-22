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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.itemanimators.AlphaInAnimator;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkingdemo.adapters.items.InfoItem;
import com.pddstudio.networkingdemo.adapters.items.PortResponseItem;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.PortService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PortResponse;

import io.inject.InjectView;
import io.inject.Injector;

public class PortScannerFragment extends Fragment implements ProcessCallback, SwipeRefreshLayout.OnRefreshListener {

    private PortService portService;
    @InjectView(R.id.portSwipeRefresh) private SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.portRecyclerView) private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter fastItemAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_port_scanner, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.inject(this, view);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        fastItemAdapter = new FastItemAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastItemAdapter);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        InfoItem infoItem = new InfoItem(getString(R.string.intro_item_header, "PortScanner"), getString(R.string.intro_port_description));
        fastItemAdapter.add(infoItem);
    }

    private void prepare() {
        portService = NetworkUtils.get(getContext(), false).getPortService(this);
        //scan all possible ports
        portService.addPortRange(1, 65535);
        portService.scan();
    }

    @Override
    public void onProcessStarted(@NonNull String serviceName) {
        Toast.makeText(getContext(), R.string.toast_port_scan_start, Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
        Toast.makeText(getContext(), R.string.toast_port_scan_fail, Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
        Toast.makeText(getContext(), R.string.toast_port_scan_finish, Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onProcessUpdate(@NonNull Object processUpdate) {
        PortResponse portResponse = (PortResponse) processUpdate;
        if(portResponse.isPortOpen()) fastItemAdapter.add(new PortResponseItem(portResponse));
    }

    @Override
    public void onRefresh() {
        if(portService != null) portService.stop();
        fastItemAdapter.clear();
        prepare();
    }
}
