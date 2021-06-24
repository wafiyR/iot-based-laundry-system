package com.example.laundryrush;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

import com.paypal.android.sdk.payments.PaymentActivity;

public class BackgroundCallback extends ScanCallback {

    //private PPPayment paymentActivity;

    //private ScanQRActivity scanQRActivity;

    private ScanQRResult scanQRResult;

    /*BackgroundCallback(PPPayment paymentActivity){
        this.paymentActivity = paymentActivity;
    }*/

    /*BackgroundCallback(ScanQRActivity scanQRActivity){
        this.scanQRActivity = scanQRActivity;
    }*/

    BackgroundCallback(ScanQRResult scanQRResult){
        this.scanQRResult = scanQRResult;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        //paymentActivity.setScanResult(result);
        //scanQRActivity.setScanResult(result);
        scanQRResult.setScanResult(result);
    }
}
