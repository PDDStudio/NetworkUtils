package com.pddstudio.networkutils;

import android.os.AsyncTask;

import com.pddstudio.networkutils.abstracts.AbstractService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ScanResult;

import java.net.InetAddress;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class SubnetScannerService extends AbstractService {

    private final ProcessCallback processCallback;
    private final String subNet;
    private int timeout;

    private NetworkScanner networkScanner;

    protected SubnetScannerService(String subNet, ProcessCallback processCallback)  {
        super(SubnetScannerService.class);
        this.processCallback = processCallback;
        this.subNet = subNet;
    }

    public SubnetScannerService setTimeout(int timeout) {
        if(timeout >= 0) {
            this.timeout = timeout;
        }
        return this;
    }

    public void startScan() {
        networkScanner = new NetworkScanner();
        networkScanner.execute();
    }

    public void stopScan() {
        if(networkScanner != null && !networkScanner.isCancelled()) {
            networkScanner.cancel(true);
        }
    }

    @Override
    public Object getResponseType() {
        return new ScanResult();
    }


    private class NetworkScanner extends AsyncTask<Void, ScanResult, Void> {

        NetworkScanner() {}

        @Override
        public void onPreExecute() {
            processCallback.onProcessStarted(SubnetScannerService.this.getServiceName());
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                InetAddress inetAddress;

                for(int i = 0; i < 256; i++) {
                    if(!isCancelled()) {
                        ScanResult scanResult = new ScanResult();
                        inetAddress = InetAddress.getByName(subNet + "." + String.valueOf(i));
                        scanResult.setIpAddress(inetAddress.getHostAddress());
                        scanResult.setHostName(inetAddress.getHostName());
                        scanResult.setCanoncialHostName(inetAddress.getCanonicalHostName());
                        if(inetAddress.isReachable(timeout)) {
                            scanResult.setReachable(true);
                        } else {
                            scanResult.setReachable(false);
                        }
                        publishProgress(scanResult);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ScanResult... results) {
            processCallback.onProcessUpdate(results[0]);
        }

        @Override
        public void onPostExecute(Void v) {
            processCallback.onProcessFinished(SubnetScannerService.this.getServiceName(), "Scanned all subnet addresses");
        }

    }

}
