package com.pddstudio.networkutils;

import android.os.AsyncTask;
import android.util.Log;

import com.pddstudio.networkutils.abstracts.AbstractService;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ScanResult;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class SubnetScannerService extends AbstractService {

    private static final boolean PRINT_LOG_MESSAGES = true;

    private static boolean interruptMultiThread = false;
    private static boolean mThreadStarted = false;

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

    public boolean isScanning()  {
        return networkScanner != null && !networkScanner.isCancelled();
    }

    public void startMultiThreadScanning() {
        mThreadStarted = true;
        processCallback.onProcessStarted(getServiceName());
        for(int i = 0; i < 129; i++) {
            SubNetScannerThread subNetScannerThread = new SubNetScannerThread(subNet + "." + String.valueOf(i));
            subNetScannerThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void interruptMultiThreadScanning() {
        interruptMultiThread = true;
        mThreadStarted = false;
        processCallback.onProcessFinished(getServiceName(), "Interrupting Multi Thread Scanning due to method call: interruptMultiThreadScanning()");
    }

    public boolean isMultiThreadScanning() {
        return !interruptMultiThread && mThreadStarted;
    }

    private void continueMultiThreadScanning() {
        for (int i = 129; i < 256; i++) {
            SubNetScannerThread subNetScannerThread = new SubNetScannerThread(subNet + "." + String.valueOf(i));
            subNetScannerThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    //TODO: rewrite SubNetScanner to scan the network faster
    private class SubNetScannerThread extends AsyncTask<Void, Void, ScanResult> {

        private InetAddress inetAddress;

        SubNetScannerThread(String address) {
            try {
                this.inetAddress = InetAddress.getByName(address);
            } catch (UnknownHostException uk) {
                uk.printStackTrace();
                cancel(true);
            }
        }

        @Override
        public void onPreExecute() {
            if(PRINT_LOG_MESSAGES) Log.d("SubNetScannerThread", "Created Thread for " + inetAddress.toString());
        }

        @Override
        protected ScanResult doInBackground(Void... params) {
            if(PRINT_LOG_MESSAGES) Log.d("SubNetScannerThread", "Executing job for " + inetAddress.getHostName());
            if(!isCancelled() && !interruptMultiThread) {
                ScanResult scanResult = new ScanResult();
                scanResult.setIpAddress(inetAddress.getHostAddress());
                scanResult.setHostName(inetAddress.getHostName());
                scanResult.setCanoncialHostName(inetAddress.getCanonicalHostName());
                try {
                    if(inetAddress.isReachable(timeout)) {
                        scanResult.setReachable(true);
                    } else {
                        scanResult.setReachable(false);
                    }
                } catch (IOException io) {
                    io.printStackTrace();
                    scanResult.setReachable(false);
                }
                return scanResult;
            }
            return null;
        }

        @Override
        public void onPostExecute(ScanResult scanResult) {
            if(interruptMultiThread) return;
            if(PRINT_LOG_MESSAGES) Log.d("SubNetScannerThread", "Finished job for " + inetAddress.toString());
            processCallback.onProcessUpdate(scanResult);
            String ip = inetAddress.toString().substring(inetAddress.toString().lastIndexOf("."));
            if(ip.contains("128")) {
                if(PRINT_LOG_MESSAGES) Log.d("SubNetScannerThread", "FOUND LAST THREAD ITEM (128) - CONTINUING WITH THE NEXT STACK");
                continueMultiThreadScanning();
            }
        }

    }

}
