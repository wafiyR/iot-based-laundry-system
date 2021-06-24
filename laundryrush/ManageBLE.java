package com.example.laundryrush;

import android.content.Intent;

import com.example.laundryrush.model.Device;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.nio.ByteBuffer;

public class ManageBLE {

    private Device device;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
    // or live (ENVIRONMENT_PRODUCTION)

    Intent intent = new Intent();

    public Intent getIntent(){
        return intent;
    }

    Intent uuid_intent = getIntent();

    // Intent intent = Intent.getIntent();

    //alarm = new JSONObject(Objects.requireNonNull(uuid_intent.getStringExtra("alarm")));


    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);

    // retrieve from QR code
    //UUID uuid = UUID.fromString(alarm.getJSONObject("originator").getString("id"));

    String qrID = device.getDeviceID();


    //byteBuffer.putLong(uuid.getMostSignificantBits());
    //byteBuffer.putLong(uuid.getLeastSignificantBits());

    //id = Arrays.toString(byteBuffer.array());


}
