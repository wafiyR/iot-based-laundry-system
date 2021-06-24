package com.example.laundryrush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.laundryrush.model.Device;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class ScanQRResult extends AppCompatActivity implements DialogInterface.OnClickListener{

    private Device device;

    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter adapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BackgroundCallback callback; // dpt result BLE - BLESCanner
    private String id, deviceID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_result);

        device = new Device();

        Intent intentUUID = getIntent();
        deviceID = intentUUID.getStringExtra("deviceID");

        // Toast.makeText(getApplicationContext(), deviceID,Toast.LENGTH_LONG).show();  // get deviceID

        handleQRResult(deviceID);
    }

    private void handleQRResult(String result){

        //device.setDeviceID(result.getText());

        device.setDeviceID(result);

        // Toast.makeText(getApplicationContext(), device.getDeviceID(),Toast.LENGTH_LONG).show();  // getdeviceID

        callback = new BackgroundCallback(this);

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

        ActivityCompat.requestPermissions(this, permission, 1);
    }

    @Override
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

    }

    @Override
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
                   /* new AlertDialog.Builder(this).setMessage("Allow this app to turn on Bluetooth?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //sessionManagement.editor.clear().apply();
                            //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();

                        }
                    }).setNegativeButton(R.string.nodialog, null).show();*/
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
    }

    public void setScanResult(ScanResult scanResult){

        ScanRecord scanRecord = scanResult.getScanRecord();

        if (scanRecord != null)
        {
            byte[] data = scanRecord.getManufacturerSpecificData(76);

            if (data != null)
            System.out.println(id + " " + deviceID + " " + Arrays.toString(data) + " " + scanRecord.getDeviceName());

            if (data != null && data.length > 16 && Arrays.toString(data).contains(id))
            {
                bluetoothLeScanner.stopScan(callback);

                //Intent intent = new Intent();

                //intent.putExtra("token", scanRecord.getDeviceName());

                // Toast.makeText(getApplicationContext(), device.getDeviceID(),Toast.LENGTH_LONG).show(); // no Toast displayed at all, no null Toast too, perhaps code didn't read this method?

                Intent intent = new Intent(ScanQRResult.this, PPPayment.class);
                //Intent intent = new Intent(ScanQRActivity.this, TestHTTP.class);
                //intent.putExtra("token", device.getDeviceID());
                intent.putExtra("token", scanRecord.getDeviceName());
                startActivity(intent);

                // Toast.makeText(getApplicationContext(), scanRecord.getDeviceName(), Toast.LENGTH_LONG).show(); // to check data stored

                //setResult(RESULT_OK, intent);

                //device.setAccessToken(scanRecord.getDeviceName());

                finish();
            }
        }
    }
}