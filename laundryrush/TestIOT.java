package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class TestIOT extends AppCompatActivity implements View.OnClickListener {

    //    private final String DEVICE_NAME="MyBTBee";
    private final String DEVICE_ADDRESS="20:13:10:15:33:66";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Button btnESP32ON;
    private Button btnESP32OFF;
    private Button btnLEDON;
    private Button btnLEDOFF;
    private Button btnBuzzON;
    private Button btnBuzzOFF;
    private Button btnToastID;

    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_i_o_t);

        btnESP32ON = findViewById(R.id.btnESP32ON);
        btnESP32OFF = findViewById(R.id.btnESP32OFF);
        btnLEDON = findViewById(R.id.btnLEDON);
        btnLEDOFF = findViewById(R.id.btnLEDOFF);
        btnBuzzON = findViewById(R.id.btnBuzzON);
        btnBuzzOFF = findViewById(R.id.btnBuzzOFF);
        btnToastID = findViewById(R.id.btnToastID);

        btnESP32ON.setOnClickListener(this);
        btnESP32OFF.setOnClickListener(this);
        btnLEDON.setOnClickListener(this);
        btnLEDOFF.setOnClickListener(this);
        btnBuzzON.setOnClickListener(this);
        btnBuzzOFF.setOnClickListener(this);
        btnToastID.setOnClickListener(this);

        if(BluetoothInit())
        {
            if(BluetoothConnect())
            {

                deviceConnected=true;
                beginListenForData();
               // textView.append("\nConnection Opened!\n");
            }

        }

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnESP32ON:
                break;

            case R.id.btnESP32OFF:
                break;

            case R.id.btnLEDON:
                break;

            case R.id.btnLEDOFF:
                break;

            case R.id.btnBuzzON:
                break;

            case R.id.btnBuzzOFF:
                break;

            case R.id.btnToastID:
                break;
        }

    }

    public boolean BluetoothInit(){
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            //startActivityForResult(enableAdapter, REQUEST_ENABLE_BT); //REQUEST_ENABLE_BT
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {   // There are paired devices. Get the name and address of each paired device.
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    //        String deviceName = device.getName();
                    //        String deviceHardwareAddress = device.getAddress(); // MAC address
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BluetoothConnect(){
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    public void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    //textView.append(string);
                                }
                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }
}
