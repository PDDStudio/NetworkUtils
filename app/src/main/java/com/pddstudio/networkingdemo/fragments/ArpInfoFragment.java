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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.itemanimators.AlphaInAnimator;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkingdemo.adapters.DataAdapter;
import com.pddstudio.networkingdemo.adapters.items.ArpInfoItem;
import com.pddstudio.networkingdemo.adapters.items.InfoItem;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.model.ArpInfo;

import io.inject.InjectView;
import io.inject.Injector;

public class ArpInfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.arpRecyclerView) private RecyclerView recyclerView;
    @InjectView(R.id.arpSwipeRefresh) private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter fastItemAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance) {
        return layoutInflater.inflate(R.layout.fragment_arp_info, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.inject(this, view);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchArpInfo();
            }
        }, 0);
    }

    private void fetchArpInfo() {
        layoutManager = new LinearLayoutManager(getContext());
        fastItemAdapter = new FastItemAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastItemAdapter);

        InfoItem infoItem = new InfoItem(getString(R.string.intro_item_header, "ARP-List"), getString(R.string.intro_arp_description));
        fastItemAdapter.add(infoItem);

        for(ArpInfo arpInfo : NetworkUtils.get(getContext(), false).getArpInfoList()) {
            ArpInfoItem arpInfoItem = new ArpInfoItem(arpInfo.getIpAddress(), arpInfo.getMacAddress());
            fastItemAdapter.add(arpInfoItem);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fastItemAdapter.clear();
        for(ArpInfo arpInfo : NetworkUtils.get(getContext(), false).getArpInfoList()) {
            ArpInfoItem arpInfoItem = new ArpInfoItem(arpInfo.getIpAddress(), arpInfo.getMacAddress());
            fastItemAdapter.add(arpInfoItem);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
