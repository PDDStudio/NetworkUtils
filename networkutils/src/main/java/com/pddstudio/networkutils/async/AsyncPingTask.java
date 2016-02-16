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

package com.pddstudio.networkutils.async;

import android.os.AsyncTask;

import com.pddstudio.networkutils.PingService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PingResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An asynchronous task to retrieve execute Ping commands using Android's native Ping binary.
 * The results will be given to the provided {@link ProcessCallback} interface.
 */
public class AsyncPingTask extends AsyncTask<Void, String, Void> {

    final ProcessCallback processCallback;
    final String targetAddress;
    final PingService pingService;

    Process process;

    public AsyncPingTask(PingService pingService, String targetAddress, ProcessCallback processCallback) {
        this.targetAddress = targetAddress;
        this.processCallback = processCallback;
        this.pingService = pingService;
    }

    @Override
    public void onPreExecute() {
        processCallback.onProcessStarted(pingService.getServiceName());
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            process = new ProcessBuilder()
                    .command("/system/bin/ping", targetAddress)
                    .redirectErrorStream(true)
                    .start();

            InputStream inputStream = process.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                if(!isCancelled()) {
                    publishProgress(line);
                } else break;
            }

        } catch (IOException io) {
            io.printStackTrace();
            cancel(true);
        } finally {
            process.destroy();
            process = null;
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        processCallback.onProcessFailed(pingService.getServiceName(), null, -1);
    }

    @Override
    protected void onProgressUpdate(String... string) {
        PingResponse pingResponse = new PingResponse();
        pingResponse.setResponseMessage(string[0]);
        processCallback.onProcessUpdate(pingResponse);
    }

}
