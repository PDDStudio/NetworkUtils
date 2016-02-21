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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.pddstudio.networkingdemo.R;
import com.pddstudio.networkingdemo.adapters.items.ScanResultItem;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.SubnetScannerService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ScanResult;

import io.inject.InjectView;
import io.inject.Injector;

public class SubnetScannerFragment extends Fragment implements ProcessCallback {

    @InjectView(R.id.subnetRecyclerView) private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter fastItemAdapter;

    private SubnetScannerService subnetScannerService;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_subnet_scanner, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.inject(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        fastItemAdapter = new FastItemAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastItemAdapter);
        startScanningSubnet();
    }

    @Override
    public void onDestroy() {
        stopScanningSubnet();
        super.onDestroy();
    }

    private void startScanningSubnet() {
        subnetScannerService = NetworkUtils.get(getContext(), false).getSubNetScannerService(this);
        subnetScannerService.startMultiThreadScanning();
    }

    private void stopScanningSubnet() {
        if(subnetScannerService != null && subnetScannerService.isMultiThreadScanning()) subnetScannerService.interruptMultiThreadScanning();
    }

    @Override
    public void onProcessStarted(@NonNull String serviceName) {
        Toast.makeText(getContext(), R.string.toast_subnet_scan_start, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
        Toast.makeText(getContext(), R.string.toast_subnet_scan_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
        Toast.makeText(getContext(), R.string.toast_subnet_scan_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessUpdate(@NonNull Object processUpdate) {
        ScanResult scanResult = (ScanResult) processUpdate;
        //only add targets if they're reachable
        if(scanResult.isReachable()) fastItemAdapter.add(new ScanResultItem(scanResult));
    }
}
