package com.example.laundryrush;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.widget.Toast;

import com.example.laundryrush.model.Device;
import com.google.zxing.Result;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler { // DialogInterface.OnClickListener

    private Device device;
    ZXingScannerView scannerView;

    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter adapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BackgroundCallback callback; // dpt result BLE - BLESCanner
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        device = new Device();

    }

    @Override
    public void handleResult(Result result) {

        //String deviceID = result.getText();
        //device.setDeviceID(result.getText().toString());
        // device.setDeviceID(result.getText().trim());
        device.setDeviceID(result.getText());

        /*callback = new BackgroundCallback(this);

        // String[] permission = Build.VERSION.SDK_INT >= 29 ? new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION} : new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
        String[] permission = Build.VERSION.SDK_INT >= 29 ? new String[] {Manifest.permission.ACCESS_FINE_LOCATION} : new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);

        //Intent intentUUID = getIntent();

        //String deviceID = intentUUID.getStringExtra("deviceID");

        String deviceID = device.getDeviceID();

        UUID uuid = UUID.fromString(deviceID);

        // UUID uuid = UUID.fromString(intent.getStringExtra("deviceID"));

        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        id = Arrays.toString(bb.array());
        id = id.substring(1, id.length() - 1);

        ActivityCompat.requestPermissions(this, permission, 1);*/

        //Toast.makeText(getApplicationContext(), result.getText(),Toast.LENGTH_LONG).show();

       // Toast.makeText(getApplicationContext(), deviceID,Toast.LENGTH_LONG).show();

        //device.setDeviceID(deviceID);

       if(device.getDeviceID() != null){  // PaymentActivity open multiple layers of same PaymentActivity
            //Intent intent = new Intent(ScanQRActivity.this, PaymentActivity.class);
           //Intent intent = new Intent(ScanQRActivity.this, PPPayment.class);
           Intent intent = new Intent(ScanQRActivity.this, ScanQRResult.class);
            //Intent intent = new Intent(ScanQRActivity.this, TestHTTP.class);
            intent.putExtra("deviceID", device.getDeviceID());
            startActivity(intent);
            //startActivity(new Intent(QRCodeActivity.this, PaymentActivity.class));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    /*@Override
    public void onClick(DialogInterface dialog, int which) {

        if (adapter.enable())
        {
            while (adapter.getState () != BluetoothAdapter.STATE_ON)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            bluetoothLeScanner = adapter.getBluetoothLeScanner();
            bluetoothLeScanner.startScan(callback);
        }
        else
            Toast.makeText(this, "Unable to turn on bluetooth.", Toast.LENGTH_LONG).show();
    }*/



    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (lm != null && !LocationManagerCompat.isLocationEnabled(lm))
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            adapter = BluetoothAdapter.getDefaultAdapter();

            if (adapter != null)
            {
                if (!adapter.isEnabled()) {
                    new AlertDialog.Builder(this).setMessage("Allow this app to turn on bluetooth?").setPositiveButton("Allow", this).setNegativeButton("Deny", null).setCancelable(false).create().show();
                   //  new AlertDialog.Builder(this).setMessage("Allow this app to turn on Bluetooth?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
                   //     @Override
                   //     public void onClick(DialogInterface dialog, int which) {

                            //sessionManagement.editor.clear().apply();
                            //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                   //         finish();

                   //     }
                    // }).setNegativeButton(R.string.nodialog, null).show();
                }else
                {
                    bluetoothLeScanner = adapter.getBluetoothLeScanner();
                    bluetoothLeScanner.startScan(callback);
                }
            }
            else
            {
                Toast.makeText(this, "No bluetooth device found.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }*/

    /*public void setScanResult(ScanResult scanResult){

        ScanRecord scanRecord = scanResult.getScanRecord();

        if (scanRecord != null)
        {
            byte[] data = scanRecord.getManufacturerSpecificData(76);

            if (data != null && data.length > 16 && Arrays.toString(data).contains(id))
            {
                bluetoothLeScanner.stopScan(callback);

                //Intent intent = new Intent();

                //intent.putExtra("token", scanRecord.getDeviceName());

                Intent intent = new Intent(ScanQRActivity.this, PPPayment.class);
                //Intent intent = new Intent(ScanQRActivity.this, TestHTTP.class);
                //intent.putExtra("token", device.getDeviceID());
                intent.putExtra("token", scanRecord.getDeviceName());
                startActivity(intent);

                // Toast.makeText(getApplicationContext(), scanRecord.getDeviceName(), Toast.LENGTH_LONG).show(); // to check data stored

                setResult(RESULT_OK, intent);

                device.setAccessToken(scanRecord.getDeviceName());

                finish();
            }
        }
    } */

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();

        if (bluetoothAdapter != null) {

            if (!bluetoothAdapter.isEnabled()) {
                new AlertDialog.Builder(this).setMessage("This app need to switch on your Bluetooth");
            } else {
                bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
                //bluetoothLeScanner.startScan(callback);

                ByteBuffer bb = ByteBuffer.wrap(new byte[16]); // hexadecimal --> device ID in MAIoT

                String deviceID = device.getDeviceID();

                UUID uuid = UUID.fromString(deviceID);

               // bb.putLong(uuid.getMostSignificantBits());
               // bb.putLong(uuid.getLeastSignificantBits());

               // deviceID = Arrays.toString(bb.array());
               // deviceID = deviceID.substring(1, deviceID.length() - 1);

                // UUID[] serviceUUIDs = new UUID[]{deviceID};
                UUID[] serviceUUIDs = new UUID[]{uuid};
                List<ScanFilter> filters = null;
                if(serviceUUIDs != null) {
                    filters = new ArrayList<>();
                    for (UUID serviceUUID : serviceUUIDs) {
                        ScanFilter filter = new ScanFilter.Builder()
                                .setServiceUuid(new ParcelUuid(serviceUUID))
                                .build();
                        filters.add(filter);
                    }
                }else{

                    Toast.makeText(getApplicationContext(), "Could not get scanner object, please scan again!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, ScanQRActivity.class));

                }

                ScanSettings scanSettings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                        .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                        .setReportDelay(0L)
                        .build();

                bluetoothLeScanner.startScan(filters, scanSettings, callback);



            }

        }// else{

            // Toast.makeText(getApplicationContext(), "Could not get scanner object, please scan again!", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(this, ScanQRActivity.class));

        // }
    }*/
}