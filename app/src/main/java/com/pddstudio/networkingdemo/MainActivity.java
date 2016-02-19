package com.pddstudio.networkingdemo;

import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.pddstudio.networkingdemo.fragments.ArpInfoFragment;
import com.pddstudio.networkutils.NetworkUtils;
import com.pddstudio.networkutils.PingService;
import com.pddstudio.networkutils.SubnetScannerService;
import com.pddstudio.networkutils.abstracts.SimpleDiscoveryListener;
import com.pddstudio.networkutils.enums.DiscoveryType;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.ArpInfo;
import com.pddstudio.networkutils.model.ConnectionInformation;
import com.pddstudio.networkutils.model.PortResponse;
import com.pddstudio.networkutils.model.ScanResult;

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

    private static final int ITEM_ARP_INFO = 0;
    private static final int ITEM_CONNECTION_INFO = 1;
    private static final int ITEM_DISCOVERY = 2;
    private static final int ITEM_PORT_SCAN = 3;
    private static final int ITEM_SUBNET_SCAN = 4;

    PingService pingService;
    SubnetScannerService subnetScannerService;
    private Drawer drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        loadDrawer(savedInstanceState);
       /* for(ArpInfo arpInfo : NetworkUtils.get(this).getArpInfoList()) {
            Log.d("MainActivity" , "ARP-IP: " + arpInfo.getIpAddress() + " ARP-MAC: " + arpInfo.getMacAddress());
        }*/

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPlaceholder, new ArpInfoFragment())
                .addToBackStack("ARPINFOFRAGMENT")
                .commit();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtils.get(MainActivity.this).scanSubNet();
            }
        }).start();*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MainActivity", "Current Device IP: " + NetworkUtils.get(MainActivity.this, false).getCurrentIpAddress());
                NetworkUtils.get(MainActivity.this, false).getConnectionInformation(conInfoCallback);
            }
        }).start();

        subnetScannerService = NetworkUtils.get(MainActivity.this, false).getSubNetScannerService(subnetScannerCallback).setTimeout(2000);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtils.get(MainActivity.this, false).getDiscoveryService().startDiscovery(DiscoveryType.WORKSTATION, new SimpleDiscoveryListener() {
                    @Override
                    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                        Log.d("MainActivity", "Found Service: " + nsdServiceInfo.getServiceName());
                    }
                });
                //Log.d("MainActivity", "Device Adblock active: " + NetworkUtils.get(MainActivity.this).checkDeviceUsesAdBlock());
                /*if(pingService == null) {

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

               // NetworkUtils.get(MainActivity.this).getPortService(portScanCallback).setTargetAddress("localhost").addPortRange(1, 9000).scan();

                if(subnetScannerService.isMultiThreadScanning()) {
                    Log.d("MainActivity", "isMultiThreadScanning() : interrupting now!");
                    subnetScannerService.interruptMultiThreadScanning();
                } else {
                    Log.d("MainActivity", "isMultiThreadScanning() : false -> starting now!");
                    subnetScannerService.startMultiThreadScanning();
                }

                NetworkUtils.get(MainActivity.this, false).getPortService(new ProcessCallback() {
                    @Override
                    public void onProcessStarted(@NonNull String serviceName) {

                    }

                    @Override
                    public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {

                    }

                    @Override
                    public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {

                    }

                    @Override
                    public void onProcessUpdate(@NonNull Object processUpdate) {
                        PortResponse portResponse = (PortResponse) processUpdate;
                        if(portResponse.isPortOpen()) Log.d("MainActivity", "Open Port detected: " + portResponse.getIpAddress());
                    }
                }).setTargetAddress(NetworkUtils.get(MainActivity.this, false).getCurrentIpAddress()).addPortRange(1, 9909).scan();

            }
        });
    }

    private void loadDrawer(Bundle savedInstanceState) {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(ITEM_ARP_INFO).withName(R.string.drawer_item_arp_info),
                        new PrimaryDrawerItem().withIdentifier(ITEM_CONNECTION_INFO).withName(R.string.drawer_item_connection_info),
                        new PrimaryDrawerItem().withIdentifier(ITEM_DISCOVERY).withName(R.string.drawer_item_discovery),
                        new PrimaryDrawerItem().withIdentifier(ITEM_PORT_SCAN).withName(R.string.drawer_item_port_scanner),
                        new PrimaryDrawerItem().withIdentifier(ITEM_SUBNET_SCAN).withName(R.string.drawer_item_subnet_scanner)
                )
                .build();
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

    private final ProcessCallback subnetScannerCallback = new ProcessCallback() {
        @Override
        public void onProcessStarted(@NonNull String serviceName) {
            Toast.makeText(MainActivity.this, "onProcessStarted()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "SubnetScannerCallback : onProcessStarted()");
        }

        @Override
        public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {
            Toast.makeText(MainActivity.this, "onProcessFailed()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "SubnetScannerCallback : onProcessFailed()");
        }

        @Override
        public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {
            Toast.makeText(MainActivity.this, "onProcessFinished()", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "SubnetScannerCallback : onProcessFinished() [" + serviceName + "::" + endMessage + "]");
        }

        @Override
        public void onProcessUpdate(@NonNull Object processUpdate) {
            if(((ScanResult) processUpdate).isReachable()) {
                Toast.makeText(MainActivity.this, "onProcessUpdate() : Target Address: " + ((ScanResult) processUpdate).getIpAddress(), Toast.LENGTH_SHORT).show();
                ScanResult scanResult = (ScanResult) processUpdate;
                Log.d("MainActivity", "ADDRESS: " + scanResult.getIpAddress() + " NAME: " + scanResult.getHostName() + " CANONCIAL NAME: " + scanResult.getCanonicalHostName());
            }
        }
    };

    private ProcessCallback conInfoCallback = new ProcessCallback() {
        @Override
        public void onProcessStarted(@NonNull String serviceName) {

        }

        @Override
        public void onProcessFailed(@NonNull String serviceName, @Nullable String errorMessage, int errorCode) {

        }

        @Override
        public void onProcessFinished(@NonNull String serviceName, @Nullable String endMessage) {

        }

        @Override
        public void onProcessUpdate(@NonNull Object processUpdate) {
            ConnectionInformation connectionInformation = (ConnectionInformation) processUpdate;
            Toast.makeText(MainActivity.this, "Your Country: " + connectionInformation.getCountry(), Toast.LENGTH_SHORT).show();
        }
    };

}
