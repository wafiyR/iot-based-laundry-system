package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.laundryrush.model.Device;
import com.example.laundryrush.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestHTTP extends AppCompatActivity implements View.OnClickListener{

    private Device deviceID;
    private Button btnTest;

    public static RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_h_t_t_p);

        requestInterface = ApiHTTP.getApiClient().create(RequestInterface.class);

        btnTest = findViewById(R.id.btnTest);

        deviceID = new Device();

        Intent intent = getIntent();
        String deviceID = intent.getStringExtra("UUID");

        Toast.makeText(getApplicationContext(), deviceID, Toast.LENGTH_LONG).show(); //Qrcode scanned device ID is successfully passed

        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnTest:
                triggerDevice();
                break;
        }

    }

    private void triggerDevice() {

        Intent intent = getIntent();
        String deviceID = intent.getStringExtra("UUID");

        Call<Device> call = requestInterface.trigger(deviceID);

        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {

                //Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();
                //Toast.makeText(TestHTTP.this,response.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {

                Toast.makeText(TestHTTP.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}