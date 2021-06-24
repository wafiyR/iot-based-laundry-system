package com.example.laundryrush;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryrush.model.Device;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PPPayment extends AppCompatActivity implements View.OnClickListener{  // , DialogInterface.OnClickListener

    private EditText etPPPayment;
    private Button btnPPPay;

    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter adapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BackgroundCallback callback; // dpt result BLE - BLESCanner

    private Device device;

    private String id;

    public static RequestInterface requestInterface;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
    // or live (ENVIRONMENT_PRODUCTION)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_p_payment);

        //requestInterface = ApiClient.getApiClient().create(RequestInterface.class);
        requestInterface = ApiHTTP.getApiClient().create(RequestInterface.class);

        device = new Device();

        etPPPayment = findViewById(R.id.etPPPayment);
        btnPPPay = findViewById(R.id.btnPPPay);

         //Intent intentUUID = getIntent();
         //String deviceID = intentUUID.getStringExtra("token"); // this is the filter

        //Toast.makeText(getApplicationContext(), deviceID, Toast.LENGTH_LONG).show(); //Qrcode scanned device ID is successfully passed

        // Start PayPal Service

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        btnPPPay.setOnClickListener(this);

        // check device ID exist or not first, if exist the proceed with paypal, if not then bring to ScanQRActivity.java then toast invalid wr code
        // startActivity(new Intent(this, ScanQRActivity.class));
        

       /* ByteBuffer bb = ByteBuffer.wrap(new byte[16]); // hexadecimal --> device ID in MAIoT

        UUID uuid = UUID.fromString(deviceID);

        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        deviceID = Arrays.toString(bb.array());
        deviceID = deviceID.substring(1, deviceID.length() - 1); */

        //callback = new BackgroundCallback(deviceID);

        // Toast.makeText(getApplicationContext(), deviceID, Toast.LENGTH_LONG).show(); // to check data stored in deviceID

        // scanResult.getDevice().getName() --> get Access Token

        // ScanResult scanResult = null; //BluetoothGatt?
        // private ScanResult scanResult; or
        // ScanResult scanResult = new ScanResult();
        // String accessToken = scanResult.getDevice().getName();
        //String accessToken = scanResult.getDevice().getName();

       // device.setDeviceID(accessToken);



        /*callback = new BackgroundCallback(this);

        // String[] permission = Build.VERSION.SDK_INT >= 29 ? new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION} : new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
        String[] permission = Build.VERSION.SDK_INT >= 29 ? new String[] {Manifest.permission.ACCESS_FINE_LOCATION} : new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);

        Intent intentUUID = getIntent();

        String deviceID = intentUUID.getStringExtra("deviceID");

        UUID uuid = UUID.fromString(deviceID);

       // UUID uuid = UUID.fromString(intent.getStringExtra("deviceID"));

        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        id = Arrays.toString(bb.array());
        id = id.substring(1, id.length() - 1);

        ActivityCompat.requestPermissions(this, permission, 1);*/

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnPPPay:

                getPayment();
                break;

        }

    }

    private void getPayment() {

        double checkpayment;

        try {
            checkpayment = Float.parseFloat(etPPPayment.getText().toString());
        }
        catch(NumberFormatException ex) {
            checkpayment = 0.00; // default ??
        }

        // how to check paypal sandbox balance? whether enough or not?

        // need to add a condition, when user insert amount more than available paypal balance, display error

        //  if(checkpayment == Float.parseFloat(null)) --> cannot be done, cause once parseFloat detects null, then error occurred

        if(checkpayment < 0){
            Toast.makeText(getApplicationContext(), "Insufficient Amount!" , Toast.LENGTH_LONG).show();
        }else if(checkpayment < 4){
            Toast.makeText(getApplicationContext(), "Insufficient Amount!" , Toast.LENGTH_LONG).show();
        }else {

            //Getting the amount from editText
            String paymentAmount = etPPPayment.getText().toString();

            //Creating a paypalpayment
            PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Bubble Rush Laundry Shop", // when change to RM, get currency error --> change to MYR
                    PayPalPayment.PAYMENT_INTENT_SALE);

            //Creating Paypal Payment activity intent
            Intent intent = new Intent(this, PaymentActivity.class);

            //putting the paypal configuration to the intent
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            //Puting paypal payment to the intent
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            //Starting the intent activity for result
            //the request code will be used on the method onActivityResult
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //If the result is from paypal
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        /*startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));*/

                        // Toast.makeText(getApplicationContext(), "Payment is Successful!" , Toast.LENGTH_LONG).show();

                        // HTTP Json - MAIoT server - trigger ESP32 once payment veriified

                        // access token is API Key

                        // String url = "https://iot.maiot.academy/dashboards/20ee22b0-b5ef-11ea-b643-7d0b18097e43";  --> invalid url

                        // HTTP - http://host:port/api/v1/$ACCESS_TOKEN/rpc  --> http://iot.maiot.academy/api/v1/$ACCESS_TOKEN/rpc  --> valid url

                        // scanResult.getDevice().getName() --> getName() get the access token

                        // HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0); -- no need

                        // http url conection

                        // retrofit - post

                        //Call<Device> call = requestInterface.trigger(device);

                        //Call<Device> call = requestInterface.trigger(device.getDeviceID());

                        Intent intentToken = getIntent();
                        final String accessToken = intentToken.getStringExtra("token");

                        // Toast.makeText(getApplicationContext(), device.getAccessToken(), Toast.LENGTH_LONG).show(); // to check data stored --> null

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HttpsURLConnection connection = (HttpsURLConnection) new URL("https://iot.maiot.academy/api/v1/" + accessToken + "/rpc").openConnection();

                                    // try use the ori version of BLE_iBeacon with LED 2

                                    connection.setDoOutput(true);
                                    connection.setRequestMethod("POST");
                                    connection.setRequestProperty("Content-Type", "application/json");
                                    connection.getOutputStream().write("{\"method\": \"x\", \"params\": {}}".getBytes());
                                    System.out.println(connection.getResponseCode());
                                    connection.disconnect();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                         // Call<Device> call = requestInterface.trigger(device.getAccessToken()); // need to pass Access Token to trigger ESP32 // device.getAccessToken() is null?

//                        Call<Device> call = requestInterface.trigger(accessToken);
//
//                        // Call<Device> call = requestInterface.trigger(accessToken);
//
//                        call.enqueue(new Callback<Device>() {
//                            @Override
//                            public void onResponse(Call<Device> call, Response<Device> response) {
//
//                                if(response.body().getResponse() == 1){  //method getResponse() from Device.java class
//
//                                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();
//
//                                }else{
//                                    Toast.makeText(PPPayment.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Device> call, Throwable t) {
//
//                                Toast.makeText(PPPayment.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
//
//                            }
//                        });

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

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
                    new AlertDialog.Builder(this).setMessage("Allow this app to turn on Bluetooth?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //sessionManagement.editor.clear().apply();
                            //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();

                        }
                    }).setNegativeButton(R.string.nodialog, null).show();
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
    } */

    //public void setScanResult(ScanResult scanResult) {

        /*ScanRecord scanRecord = scanResult.getScanRecord();
        long time = System.currentTimeMillis();
        boolean found = false; */

        //Intent intent = getIntent();
        //String deviceID = intent.getStringExtra("UUID");

        // String accessToken = scanResult.getDevice().getName();

        // device.setAccessToken(accessToken);

        // device.setDeviceID(accessToken);

        // Big endient and Little Endient - need to reverse
        // ByteBuffer
        // UUID


        /*if(scanRecord != null){

            byte[] data = scanRecord.getManufacturerSpecificData(76);

            // access token is API Key

            // HTTP - http://host:port/api/v1/$ACCESS_TOKEN/rpc  --> http://iot.maiot.academy/api/v1/$ACCESS_TOKEN/rpc

            // UUID uuid = UUID.fromString(deviceID);

            if(data != null && data.length > 16){  // compare scanRecord BLE with camera UUID/deviceID  //data.length > 16 // data.equals(uuid) // data.equals(deviceID)

                // send data to maiot to trigger function in ESP32
                Toast.makeText(getApplicationContext(), deviceID, Toast.LENGTH_LONG).show();
                found = Arrays.toString(data).contains(deviceID); // found = Arrays.toString(data).contains(id)

            }

        }else{
            if(found){
                bluetoothLeScanner.stopScan(callback);
            }
        } */



        /*ScanRecord scanRecord = scanResult.getScanRecord();

        if (scanRecord != null)
        {
            byte[] data = scanRecord.getManufacturerSpecificData(76);

            if (data != null && data.length > 16 && Arrays.toString(data).contains(id))
            {
                bluetoothLeScanner.stopScan(callback);

                Intent intent = new Intent();

                intent.putExtra("token", scanRecord.getDeviceName());

                // Toast.makeText(getApplicationContext(), scanRecord.getDeviceName(), Toast.LENGTH_LONG).show(); // to check data stored

                setResult(RESULT_OK, intent);

                device.setAccessToken(scanRecord.getDeviceName());

                finish();
            }
        }

    }*/

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
}