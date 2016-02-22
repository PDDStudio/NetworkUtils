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
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkingdemo.adapters.items.ConnectionInformationItem;
import com.pddstudio.networkingdemo.adapters.items.InfoItem;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ConnectionInformation;

import io.inject.InjectView;
import io.inject.Injector;

public class ConnectionInfoFragment extends Fragment implements ProcessCallback, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.conSwipeRefresh) private SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.conRecyclerView) private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter fastItemAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_connection_info, viewGroup, false);
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
        InfoItem infoItem = new InfoItem(getString(R.string.intro_item_header, "Connection-Information"), getString(R.string.intro_con_desc));
        fastItemAdapter.add(infoItem);
    }

    private void fetchConnectionInfo() {
        NetworkUtils.get(getContext(), false).getConnectionInformation(this);
    }

    @Override
    public void onProcessStarted(@NonNull String serviceName) {
        swipeRefreshLayout.setRefreshing(true);
        Toast.makeText(getContext(), R.string.toast_con_info_start, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.toast_con_info_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.toast_con_info_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessUpdate(@NonNull Object processUpdate) {
        ConnectionInformation connectionInformation = (ConnectionInformation) processUpdate;
        ConnectionInformationItem connectionInformationItem = new ConnectionInformationItem(connectionInformation);
        fastItemAdapter.add(connectionInformationItem);
        onProcessFinished("", null);
    }

    @Override
    public void onRefresh() {
        fastItemAdapter.clear();
        fetchConnectionInfo();
    }
}
