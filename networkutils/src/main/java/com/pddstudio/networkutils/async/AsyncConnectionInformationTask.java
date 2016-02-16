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
