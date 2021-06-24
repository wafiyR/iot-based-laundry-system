package com.example.laundryrush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laundryrush.model.Device;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.util.List;

public class QRCodeActivity extends AppCompatActivity {

    private CameraView cameraView;
    private boolean isDetected = false;
    private Button btnScan;

    private Device device;

    FirebaseVisionBarcodeDetectorOptions fbOptions;
    FirebaseVisionBarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        device = new Device();

        setupCamera();

/*        Dexter.withActivity(this).withPermission(new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            setupCamera();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        });*/       // have errors at .withListener and String [], can't run the code, but this part of code is not that necessary
                    // when [] is removed from String, .withListener won't have errors, but the whole code will, and vice versa



    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }



    public void setupCamera(){

        btnScan = findViewById(R.id.btnScan);
        btnScan.setEnabled(isDetected);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDetected = !isDetected;
            }
        });

        cameraView = findViewById(R.id.cameraView);
        cameraView.setLifecycleOwner(this);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(@NonNull CameraOptions options) {
                super.onCameraOpened(options);
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }
        });

        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                processImage(getVisionImageFromFrame(frame));
            }
        });

        fbOptions = new FirebaseVisionBarcodeDetectorOptions.Builder().setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE).build(); //, FirebaseVisionBarcode.FORMAT_DATA_MATRIX

        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(fbOptions);



    }

    private synchronized void processImage(FirebaseVisionImage image) {

        if(!isDetected){
            detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                @Override
                public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                    
                    processResult(firebaseVisionBarcodes);

                }
            });/*.addOnFailureListener(new onFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e){
                    Toast.makeText(QRCodeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });*/
        }
    }

    private synchronized void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {

        if(firebaseVisionBarcodes.size() > 0){
            isDetected = true;
            btnScan.setEnabled(isDetected);

            for (FirebaseVisionBarcode item: firebaseVisionBarcodes){

            //FirebaseVisionBarcode item = new FirebaseVisionBarcode(firebaseVisionBarcodes);

                int value_type = item.getValueType();  // if item.getValueType return TYPE_TEXT - other type example: TYPE_EMAIL, TYPE_PHONE, TYPE_URL etc

                switch(value_type){
                    case FirebaseVisionBarcode.TYPE_TEXT:

                        // createDialog(item.getRawValue());   // Returns barcode value as it was encoded in the barcode.

                        device.setDeviceID(item.getRawValue());

                        CameraListener closeCamera = new CameraListener() {
                            @Override
                            public void onCameraClosed() {
                                super.onCameraClosed();

                            }
                        };


                        // the error of multiple qr codes reading still occur, cause when qrcode is scanned, alert dialog prompt multiple times, dont know how to fix it, dont care
                        // fortunately, didn't even matter with the error, just need to pass the qrcode scanned value to a variable, then sth about BLE connection

                        // getter and setter for qrscanned value




                        //String rawValue = item.getRawValue();
                        //Toast.makeText(getApplicationContext(), rawValue,Toast.LENGTH_LONG).show();
                        break;

                }

               // cameraView.release();

                break;

/*                int count = 0;

                if(count == 1){
                    break;
                }

                count++;*/ // try to break from more than one in for loop, failed

                //break;
            }

            // call ManageBLE method

            //Toast.makeText(getApplicationContext(), device.getDeviceID(),Toast.LENGTH_LONG).show();

/*            if(device.getDeviceID() != null){  // PaymentActivity open multiple layers of same PaymentActivity
                startActivity(new Intent(QRCodeActivity.this, PaymentActivity.class));
            }*/


        }

        if(device.getDeviceID() != null){  // PaymentActivity open multiple layers of same PaymentActivity
            /*Intent intent = new Intent(QRCodeActivity.this, PaymentActivity.class);
            intent.putExtra("UUID", device.getDeviceID());
            startActivity(intent); */
            //startActivity(new Intent(QRCodeActivity.this, PaymentActivity.class));
        }

    }

    private void createDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {

        byte [] data = frame.getData();

        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setWidth(frame.getSize().getWidth())   // 480x360 is typically sufficient for
                .setHeight(frame.getSize().getHeight())  // image recognition
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                //.setRotation(frame.getRotation()) // only used if want to work in landscape mode, never use in portrait
                .build();

        return FirebaseVisionImage.fromByteArray(data,metadata);
    }


}
