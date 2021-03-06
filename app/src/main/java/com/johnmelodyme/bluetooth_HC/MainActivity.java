package com.johnmelodyme.bluetooth_HC;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @Author: JOHN MELODY MELISSA
 * @Project: BLUETOOTH HC-05
 * @Inpired:  GF TAN SIN DEE <3
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private int REQUEST_ACCESS_COARSE_LOACTION = 0x1;
    private Context Main = MainActivity.this;
    private BluetoothAdapter bluetoothAdapter;
    ArrayList<String> ScannedDevice;
    ArrayList<String> AllDevice;
    ListView Scanned_device_list;

    @Override
    public void onStart(){
        super.onStart();
        if (bluetoothAdapter == null){
            new SweetAlertDialog(Main, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...I\'m So Sorry.")
                    .setContentText("This Device Doesn\'t support Bluetooth.")
                    .show();
            Log.w(TAG, "Bluetooth" + ":" + "User Device Doesn\'t Support Bluetooth {0}");
        } else {
            if (bluetoothAdapter.isEnabled()){
                // REQUEST USER TO ```ALLOW``` and turn on Location:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_ACCESS_COARSE_LOACTION);
                Log.w(TAG, "Bluetooth" + ":" + "Already on {1}");
            } else {
                new SweetAlertDialog(Main, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Turn On Bluetooth?")
                        .setContentText("This Application Won\'t Work Without Bluetooth Enabled. ")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                bluetoothAdapter.enable();
                                // REQUEST USER TO ```ALLOW``` and turn on Location:
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                }, REQUEST_ACCESS_COARSE_LOACTION);
                                Log.w(TAG, "Bluetooth" + ":" + "Enabling Bluetooth {1}");
                                Log.w(TAG, "Bluetooth" + ":" + "Bluetooth Enabled {1}");
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                SweetAlertDialog pDialog = new SweetAlertDialog(Main, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Exiting...");
                                pDialog.setCancelable(false);
                                pDialog.show();
                                Log.w(TAG, "Bluetooth" + ":" + "USer Cancel on Bluetooth Request {-1}");
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ScannedDevice = new ArrayList<>();
        AllDevice = new ArrayList<>();
        Scanned_device_list = findViewById(R.id.ListViewSDevice);

        if (AllDevice != null){
            // Clear Array for Refresh List:
            AllDevice.clear();
        }

        // TODO :: {Terminal Page :: RECYCLER_VIEW}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.source) {
            String URL;
            URL = getResources().getString(R.string.url);
            Intent SOURCE_CODE;
            SOURCE_CODE = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(SOURCE_CODE);
            return true;
        }

        if (id == R.id.about) {
            String ABOUT;
            ABOUT = getResources().getString(R.string.about_me);
            Intent ABOUT_ME;
            ABOUT_ME = new Intent(Intent.ACTION_VIEW, Uri.parse(ABOUT));
            startActivity(ABOUT_ME);
            return true;
        }

        if (id == R.id.bluetooth_list){
            // TODO :: {ListOfScannedDevice} : dialogue
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        bluetoothAdapter.disable();
    }
}
