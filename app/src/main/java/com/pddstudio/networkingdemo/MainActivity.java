package com.pddstudio.networkingdemo;

import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.PingService;
import com.pddstudio.networkutils.abstracts.SimpleDiscoveryListener;
import com.pddstudio.networkutils.enums.DiscoveryType;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PingResponse;
import com.pddstudio.networkutils.model.PortResponse;

public class MainActivity extends AppCompatActivity {

    private static final String SERVICE_TYPE_DISPLAY = "_barco-dramp._tcp.";
    private static final String SERVICE_TYPE_GBCMC = "_workstation._tcp.";
    private static final String SERVICE_TYPE_HTTP = "_http._tcp";
    private static final String SERVICE_TYPE_JENKINS = "_jenkins._tcp";
    private static final String SERVICE_TYPE_HUDSON = "_hudson._tcp";
    private static final String SERVICE_TYPE_VNC_REMOTE = "_rfb._tcp";
    private static final String SERVICE_TYPE_SSH = "_ssh._tcp";
    private static final String SERVICE_TYPE_REMOTE_DISK_MANAGEMENT = "_udisks-ssh._tcp";
    private static final String SERVICE_TYPE_RTSP = "_rtsp._tcp";

    PingService pingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NetworkUtils.get(MainActivity.this).getDiscoveryService().startDiscovery(DiscoveryType.SSH_SERVER, new SimpleDiscoveryListener() {
                    @Override
                    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                        Log.d("MainActivity", "Found Service: " + nsdServiceInfo.getServiceName());
                    }
                });
                Log.d("MainActivity", "Device Adblock active: " + NetworkUtils.get(MainActivity.this).checkDeviceUsesAdBlock());
                if(pingService == null) {

                    pingService = NetworkUtils.get(MainActivity.this).getPingService(new ProcessCallback() {
                        @Override
                        public void onProcessStarted(@NonNull String serviceName) {
                            Log.d("MainActivity", "Process started: " + serviceName);
                        }

                        @Override
                        public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
                            Log.d("MainActivity", "Process failed: " + serviceName);
                        }

                        @Override
                        public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
                            Log.d("MainActivity", "Process finidshed: " + serviceName);
                        }

                        @Override
                        public void onProcessUpdate(@NonNull Object processUpdate) {
                            Log.d("MainActivity", "Ping Response: " + ((PingResponse) processUpdate).getResponseMessage());
                        }
                    }).setTargetAddress("www.google.com");

                } else if(pingService.isRunning()) {
                    pingService.destroy();
                } else {
                    pingService.start();
                }*/

                NetworkUtils.get(MainActivity.this).getPortService(portScanCallback).setTargetAddress("localhost").addPortRange(1, 9000).scan();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ProcessCallback portScanCallback = new ProcessCallback() {
        @Override
        public void onProcessStarted(@NonNull String serviceName) {
            Toast.makeText(MainActivity.this, "onProcessStarted()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Started Service: " + serviceName);
        }

        @Override
        public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
            Toast.makeText(MainActivity.this, "onProcessFailed()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Failed Service : " + serviceName);
        }

        @Override
        public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
            Toast.makeText(MainActivity.this, "onProcessFinished()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Finished Service: " + serviceName);
        }

        @Override
        public void onProcessUpdate(@NonNull Object processUpdate) {
            PortResponse portResponse = (PortResponse) processUpdate;
            Log.d("MainActivity", "Target: " + portResponse.getIpAddress() + " Port: " + portResponse.getPort() + " Open: " + portResponse.isPortOpen() + " Message: " + (portResponse.getMessage() != null ? portResponse.getMessage() : ""));
        }
    };

}
