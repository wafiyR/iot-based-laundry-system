package com.example.laundryrush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LaundryLocationActivity extends AppCompatActivity {

    private static final String TAG = "" ;
    private static final int REQUEST_LOCATION_PERMISSION = 0;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_location);

        webView = findViewById(R.id.webView);

        loadLink();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadLink() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webSetting.setJavaScriptEnabled(true);
        //webView.loadUrl("http://www.javapapers.com");
        //webView.loadUrl("https://www.google.com/search?q=laundry+near+me&oq=laundry+near&aqs=chrome.0.0j69i57j0l2.3047j0j7&client=ms-android-huawei-rev1&sourceid=chrome-mobile&ie=UTF-8&dlnr=1&tbs=lrf:!1m6!1u8!3m3!8m2!1u805!3e1!4e1!1m5!1u3!2m2!3m1!1e1!4e1!1m5!1u2!2m2!2m1!1e1!4e1!2m4!1e17!4m2!17m1!1e2!2m1!1e2!2m1!1e8!2m1!1e3!3sIAE%3D&ibp=gwp;0,6&sa=X&ved=2ahUKEwjS2LWI2-LpAhXdILcAHbtiAcAQ3G0oAHoECAsQDg#aq=laundry%20near%20me&dlnr=1&rltbs=lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!1m5!1u8!2m3!8m2!1u805!3e1!2m1!1e8!2m1!1e2!2m1!1e3!2m4!1e17!4m2!17m1!1e2!3sIAE,lf:1,lf_ui:14");
        webView.loadUrl("https://www.google.com/search?q=laundry+near+me&oq=laundry+near&aqs=chrome.0.0j69i57j0l2.3047j0j7&client=ms-android-huawei-rev1&sourceid=chrome-mobile&ie=UTF-8&dlnr=1&sei=jQ3WXrWSA_riz7sPoaW9uAE");
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d(TAG, "getLocation: permissions granted");
        }
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

            //mGeolocationRequestOrigin = null;
           // super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };

    //webView.setWebChromeClient(mWebChromeClient);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
