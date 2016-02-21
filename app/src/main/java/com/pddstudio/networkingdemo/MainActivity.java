package com.pddstudio.networkingdemo;

import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.pddstudio.networkingdemo.fragments.ArpInfoFragment;
import com.pddstudio.networkingdemo.fragments.ConnectionInfoFragment;
import com.pddstudio.networkingdemo.fragments.DiscoveryFragment;
import com.pddstudio.networkingdemo.fragments.PortScannerFragment;
import com.pddstudio.networkingdemo.fragments.SubnetScannerFragment;
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

    private static final int ITEM_ARP_INFO = 1;
    private static final int ITEM_CONNECTION_INFO = 2;
    private static final int ITEM_DISCOVERY = 3;
    private static final int ITEM_PORT_SCAN = 4;
    private static final int ITEM_SUBNET_SCAN = 5;

    PingService pingService;
    SubnetScannerService subnetScannerService;
    private Drawer drawer;
    private AccountHeader accountHeader;
    private Toolbar toolbar;
    private long currentDrawerItem = ITEM_ARP_INFO;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        loadDrawer(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPlaceholder, new ArpInfoFragment())
                .addToBackStack(null)
                .commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MainActivity", "Current Device IP: " + NetworkUtils.get(MainActivity.this, false).getCurrentIpAddress());
                NetworkUtils.get(MainActivity.this, false).getConnectionInformation(conInfoCallback);
            }
        }).start();

        subnetScannerService = NetworkUtils.get(MainActivity.this, false).getSubNetScannerService(subnetScannerCallback).setTimeout(2000);

    }

    private void loadDrawer(Bundle savedInstanceState) {

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withDividerBelowHeader(true)
                .withProfileImagesVisible(false)
                .withSelectionListEnabled(false)
                .withAlternativeProfileHeaderSwitching(false)
                .addProfiles(new ProfileDrawerItem().withName(getString(R.string.app_name)).withEmail(String.format(getString(R.string.drawer_header_version), BuildConfig.VERSION_NAME)))
                .withHeaderBackground(R.color.colorPrimary)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(ITEM_ARP_INFO).withName(R.string.drawer_item_arp_info),
                        new PrimaryDrawerItem().withIdentifier(ITEM_CONNECTION_INFO).withName(R.string.drawer_item_connection_info),
                        new PrimaryDrawerItem().withIdentifier(ITEM_DISCOVERY).withName(R.string.drawer_item_discovery),
                        new PrimaryDrawerItem().withIdentifier(ITEM_PORT_SCAN).withName(R.string.drawer_item_port_scanner),
                        new PrimaryDrawerItem().withIdentifier(ITEM_SUBNET_SCAN).withName(R.string.drawer_item_subnet_scanner)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem != null && drawerItem.getIdentifier() != currentDrawerItem) {
                            currentDrawerItem = drawerItem.getIdentifier();
                            if(drawerItem.getIdentifier() == ITEM_ARP_INFO) switchPage(new ArpInfoFragment());
                            else if(drawerItem.getIdentifier() == ITEM_CONNECTION_INFO) switchPage(new ConnectionInfoFragment());
                            else if(drawerItem.getIdentifier() == ITEM_DISCOVERY) switchPage(new DiscoveryFragment());
                            else if(drawerItem.getIdentifier() == ITEM_PORT_SCAN) switchPage(new PortScannerFragment());
                            else if(drawerItem.getIdentifier() == ITEM_SUBNET_SCAN) switchPage(new SubnetScannerFragment());
                        }
                        if(drawer.isDrawerOpen()) drawer.closeDrawer();
                        return true;
                    }
                })
                .withSelectedItem(ITEM_ARP_INFO)
                .build();
    }

    private void switchPage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPlaceholder, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if(currentDrawerItem != ITEM_ARP_INFO) {
            drawer.setSelection(ITEM_ARP_INFO, true);
        } else {
            this.finish();
            System.exit(0);
        }
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
