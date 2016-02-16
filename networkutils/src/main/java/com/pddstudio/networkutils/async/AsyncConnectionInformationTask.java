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

import com.google.gson.Gson;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ConnectionInformation;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An asynchronous task to retrieve an {@link ConnectionInformation} instance.
 * The result will be given to the provided {@link ProcessCallback} interface.
 */
public class AsyncConnectionInformationTask extends AsyncTask<Void, Void, ConnectionInformation> {

    private static final String SERVICE_NAME = AsyncConnectionInformationTask.class.getSimpleName();

    final ProcessCallback processCallback;

    public AsyncConnectionInformationTask(ProcessCallback processCallback) {
        this.processCallback = processCallback;
    }

    @Override
    public void onPreExecute() {
        processCallback.onProcessStarted(SERVICE_NAME);
    }

    @Override
    protected ConnectionInformation doInBackground(Void... params) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();

        try {

            Request request = new Request.Builder().url("http://www.ip-api.com/json").build();
            Response response = okHttpClient.newCall(request).execute();
            if(!response.isSuccessful()) throw new IOException("Request failed: " + response);
            return gson.fromJson(response.body().charStream(), ConnectionInformation.class);

        } catch (IOException io) {
            io.printStackTrace();
            cancel(true);
        }


        return null;
    }

    @Override
    protected void onCancelled() {
        processCallback.onProcessFailed(SERVICE_NAME, "UNABLE TO PERFORM ACTION", -1);
    }

    @Override
    public void onPostExecute(ConnectionInformation connectionInformation) {
        processCallback.onProcessUpdate(connectionInformation);
    }

}
