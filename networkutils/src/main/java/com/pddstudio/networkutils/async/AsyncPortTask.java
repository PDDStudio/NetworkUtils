package com.pddstudio.networkutils.async;

import android.os.AsyncTask;

import com.pddstudio.networkutils.PortService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PortResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class AsyncPortTask extends AsyncTask<Void, PortResponse, Void> {

    final PortService portService;
    final List<Integer> portList;
    final ProcessCallback processCallback;
    final String target;

    public AsyncPortTask(PortService portService, String target, List<Integer> portList, ProcessCallback processCallback) {
        this.portService = portService;
        this.portList = portList;
        this.processCallback = processCallback;
        this.target = target;
    }

    @Override
    public void onPreExecute() {
        processCallback.onProcessStarted(portService.getServiceName());
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            InetAddress inetAddress = InetAddress.getByName(target);
            Socket socket;

            for(Integer port : portList) {
                if(!isCancelled()) {

                    PortResponse portResponse = new PortResponse();
                    portResponse.setIpAddress(target);
                    portResponse.setPort(port);
                    try {
                        socket = new Socket(inetAddress, port);
                        socket.setSoTimeout(1000);
                        portResponse.setPortOpen(true);
                    } catch (IOException io) {
                        portResponse.setMessage(io.getMessage());
                        portResponse.setPortOpen(false);
                    }

                    publishProgress(portResponse);

                } else break;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        processCallback.onProcessFailed(portService.getServiceName(), null, -1);
    }

    @Override
    protected void onProgressUpdate(PortResponse... portResponses) {
        processCallback.onProcessUpdate(portResponses[0]);
    }

}
