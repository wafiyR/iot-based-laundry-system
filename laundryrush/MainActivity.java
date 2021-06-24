package com.example.laundryrush;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryrush.model.User;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgMachineCycle;
    private ImageView imgReloadWallet;
    private ImageView imgQRPayment;
    private ImageView imgLaundryLocation;
    private ImageView imgProfileSetting;

    private TextView lblUserProfile;
    private TextView lblReloadCard;
    private TextView lblpayment;
    private TextView lblNearbyLaundry;

    private ImageView imgCycle;
    private TextView lblImgCycle;

    private SessionManagement sessionManagement;

    //private User user;

    //public static PreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imgMachineCycle = findViewById(R.id.imgCycle);
        //imgProfileSetting = findViewById(R.id.imgSetting);
        imgReloadWallet = findViewById(R.id.imgCard);
        imgQRPayment = findViewById(R.id.imgQRCode);
        imgLaundryLocation = findViewById(R.id.imgLocation);
        imgCycle = findViewById(R.id.imgCycle);


        //lblUserProfile = findViewById(R.id.lblUserProfile);
        lblReloadCard = findViewById(R.id.lblReloadCard);
        lblpayment = findViewById(R.id.lblpayment);
        lblImgCycle = findViewById(R.id.lblImgCycle);
        lblNearbyLaundry = findViewById(R.id.lblNearbyLaundry);

        sessionManagement = new SessionManagement(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManagement.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
       //sessionManagement.checkLogin();

        // get user data from session
        //HashMap<String, String> user = sessionManagement.getUserDetails();

       //String username = user.get(SessionManagement.KEY_NAME);

       //String password = user.get(SessionManagement.KEY_PASSWORD);

        //Toast.makeText(getApplicationContext(), username,Toast.LENGTH_LONG).show();


        //imgMachineCycle.setOnClickListener(this);
        //imgProfileSetting.setOnClickListener(this);
        imgReloadWallet.setOnClickListener(this);
        imgQRPayment.setOnClickListener(this);
        imgLaundryLocation.setOnClickListener(this);
        imgCycle.setOnClickListener(this);

        //lblUserProfile.setOnClickListener(this);
        lblReloadCard.setOnClickListener(this);
        lblpayment.setOnClickListener(this);
        lblImgCycle.setOnClickListener(this);
        lblNearbyLaundry.setOnClickListener(this);




       // user.setUsername("Try");

        //preferenceConfig = new PreferenceConfig(this);

/*        Intent login = new Intent(this, LoginActivity.class);

        startActivityForResult(login, 1);*/

        // < --- >

        // somehow this part of codes cause the app to stop working a.k.a crash

/*        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {

                    case R.id.imgCycle:

                        Intent intent_machineCycle = new Intent(MainActivity.this, MachineCycleActivity.class);
                        startActivity(intent_machineCycle);
                        break;

                    case R.id.imgCard:

                        Intent intent_reloadWallet = new Intent(MainActivity.this, EWalletActivity.class);
                        startActivity(intent_reloadWallet);
                        break;

                }

            }
        };

        imgMachineCycle.setOnClickListener(listener);
        imgReloadWallet.setOnClickListener(listener);*/

        // < --- >

        // somehow this part of codes cause the app to stop working a.k.a crash

/*        imgMachineCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_machineCycle = new Intent(MainActivity.this, MachineCycleActivity.class);
                startActivity(intent_machineCycle);
            }
        });

        imgReloadWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_reloadWallet = new Intent(MainActivity.this, EWalletActivity.class);
                startActivity(intent_reloadWallet);
            }
        });

        imgQRPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent_qrPayment = new Intent(MainActivity.this, Pay)
            }
        });

        imgLaundryLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_laundryLocation = new Intent(MainActivity.this, LaundryLocationActivity.class);
                startActivity(intent_laundryLocation);
            }
        });*/

     // < --- >
    }

    public void onClick(View view){
        switch(view.getId()){

/*            case R.id.imgCycle:

                //doUserRegistration();
                //Toast.makeText(getApplicationContext(), "Register Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MachineCycleActivity.class));
                break;*/

            /*case R.id.imgSetting:

            case R.id.lblUserProfile:

                startActivity(new Intent(this, UserProfileActivity.class));

                break;*/

            case R.id.lblReloadCard:

            case R.id.imgCard:

                startActivity(new Intent(this, EWalletActivity.class));

                break;

            case R.id.lblpayment:

            case R.id.imgQRCode:

                //startActivity(new Intent(this, QRCodeActivity.class));
                startActivity(new Intent(this, ScanQRActivity.class));

                break;

            case R.id.lblNearbyLaundry:

            case R.id.imgLocation:

                startActivity(new Intent(this, MapsActivity.class));

                break;

            case R.id.lblImgCycle:

            case R.id.imgCycle:

                //startActivity(new Intent(this, LaundryLocationActivity.class));
                startActivity(new Intent(this, CountDownActivity.class));
                //startActivity(new Intent(this, TestIOT.class));

                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                sessionManagement.editor.clear().apply();
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        }).setNegativeButton(R.string.nodialog, null).show();
    }

    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            setContentView(R.layout.activity_main);

            TextView txtGreeting = findViewById(R.id.txtGreeting);

            if (data != null)
                txtGreeting.setText("Hello, " + data.getAction());
        }
    }*/

    // to select methods to override, press CTRL + O, and then search for the methods


    //to implement options menu (the three dots menu)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            
/*            case R.id.recentLaundry:
                //Intent intent_recentLaundry = new Intent(MainActivity.this, )
                return true;

            case R.id.myprofile:

                return true;

            case R.id.myCompany:

                Intent intent_myCompany = new Intent(MainActivity.this, CompanyListActivity.class);
                startActivity(intent_myCompany);
                return true;*/

            case R.id.logout:
               // sessionManagement.logoutUser();

                new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sessionManagement.editor.clear().apply();
                        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                    }
                }).setNegativeButton(R.string.nodialog, null).show();

                // alertdialog didnt work because once logout is click, it will instantly logout because of SessionManagement.java
 /*               new AlertDialog.Builder(this).setMessage("Are you sure you want to logout?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sessionManagement.editor.clear().apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                    }
                }).setNegativeButton(R.string.nodialog, null).show();*/
                break;

            case R.id.faq:
                break;


        }

        return super.onOptionsItemSelected(item);
    }

/*    public void shiftToMachineCycleActivity(View view) {
        Intent intent_machineCycle = new Intent(MainActivity.this, MachineCycleActivity.class);
        startActivity(intent_machineCycle);

    }

    public void shiftToEWalletActivity(View view) {
        Intent intent_reloadWallet = new Intent(MainActivity.this, EWalletActivity.class);
        startActivity(intent_reloadWallet);

    }

    public void shiftToPaymentActivity(View view) {
        //Intent intent_qrPayment = new Intent(MainActivity.this, )

    }

    public void shiftToLaundryLocationActivity(View view) {
        Intent intent_laundryLocation = new Intent(MainActivity.this, LaundryLocationActivity.class);
        startActivity(intent_laundryLocation);

    }*/
}
