package com.example.laundryrush;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryrush.model.User;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lblviewBalance;
    private EditText etbalance;
    private Button btnReload;

    private User user;

    private SessionManagement sessionManagement;

    public static RequestInterface requestInterface;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 234;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
    // or live (ENVIRONMENT_PRODUCTION)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_wallet);

        //lblviewBalance = findViewById(R.id.lblviewBalance);
        etbalance = findViewById(R.id.etbalance);
        btnReload = findViewById(R.id.btnReload);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);


        //displayBalance();

        user =  new User();

        sessionManagement = new SessionManagement(getApplicationContext());

        HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

        //Toast.makeText(getApplicationContext(), sp_username,Toast.LENGTH_LONG).show();

        // Start PayPal Service

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        btnReload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnReload:
                //doReloadBalance();
                doReloadPaypal();
                break;
        }

    }

    private void doReloadPaypal() {

        double checkpayment;

        try {
            checkpayment = Float.parseFloat(etbalance.getText().toString());
        }
        catch(NumberFormatException ex) {
            checkpayment = 0.00; // default ??
        }

        // how to check paypal sandbox balance? whether enough or not?

        // need to add a condition, when user insert amount more than available paypal balance, display error

        //  if(checkpayment == Float.parseFloat(null)) --> cannot be done, cause once parseFloat detects null, then error occurred

        if(checkpayment <= 0){
            Toast.makeText(getApplicationContext(), "Insufficient Amount!" , Toast.LENGTH_LONG).show();
        }else {

            //Getting the amount from editText
            String paymentAmount = etbalance.getText().toString();

            //Creating a paypalpayment
            PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "MYR", "Bubble Rush Laundry Shop", // when change to RM, get currency error
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

                        Toast.makeText(getApplicationContext(), "Payment is Successful!" , Toast.LENGTH_LONG).show();

                        // HTTP Json - MAIoT server - trigger ESP32 once payment veriified

                        String url = "https://iot.maiot.academy/dashboards/20ee22b0-b5ef-11ea-b643-7d0b18097e43";

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

    private void displayBalance() {   // might need to use SimpleAsynTask to update the UI(TextView of lblviewBalance) automatically when changes occur without reloading the acivity. Example is SimpleAsyTask project of Lab Exercise 7

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        final HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

        Call<List<User>> call = requestInterface.getBalance(sp_username);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                List<User> userDetails = response.body();

                String balance = userDetails.get(0).geteWalletbalance();
                //float balance = userDetails.get(0).geteWalletbalance();

                //String str_balance = String.format("%.2f", balance);

                lblviewBalance.setText(balance);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Toast.makeText(EWalletActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }


    /*private void doReloadBalance() {

        user.seteWalletbalance(etbalance.getText().toString().trim());

        String balance = user.geteWalletbalance();

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

        Call<User> call = requestInterface.updateBalance(sp_username, balance);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body().getResponse() == 1){

                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                    Intent reloadActivity = new Intent();
                    reloadActivity = getIntent();

                    finish();

                    startActivity(reloadActivity);



                }else{
                    Toast.makeText(EWalletActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(EWalletActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }*/

}
