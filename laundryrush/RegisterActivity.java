package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laundryrush.dbSQL.DatabaseHelper;
import com.example.laundryrush.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail;
    private EditText txtUsernameRegister;
    private EditText txtPasswordRegister;
    private EditText txtConfirmPassword;
    private Button btnRegister;
    private Button btngoLogin;

    //private DatabaseHelper databaseHelper;
    private User user;

    public static RequestInterface requestInterface;
    //public static PreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);
        //preferenceConfig = new PreferenceConfig(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtUsernameRegister = findViewById(R.id.txtUsernameRegister);
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister);
        // txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister = findViewById(R.id.btnSubmitRegister);
        btngoLogin = findViewById(R.id.btngoLogin);

        //databaseHelper = new DatabaseHelper(this);
        user = new User();

        btnRegister.setOnClickListener(this);
        btngoLogin.setOnClickListener(this);

        // somehow this part of codes cause the app to stop working a.k.a crash

/*        btngoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent_goLogin);
            }
        });*/

    }

    public void onClick(View view){
        switch(view.getId()){

            case R.id.btnSubmitRegister:

                doUserRegistration();
                //Toast.makeText(getApplicationContext(), "Register Successfully",Toast.LENGTH_LONG).show();
                break;

            case R.id.btngoLogin:

                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void doUserRegistration(){

        //defining a progress dialog to show while signing up
/*        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();*/

        user.setEmail(txtEmail.getText().toString().trim());
        user.setUsername(txtUsernameRegister.getText().toString().trim());
        user.setPassword(txtPasswordRegister.getText().toString().trim());

        String user_email = user.getEmail();
        String user_name = user.getUsername();
        String password = user.getPassword();

        /*databaseHelper.addUser(user);*/

        //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost/laundryRush/api/").addConverterFactory(GsonConverterFactory.create()).build();

        // 10.0.2.2 is a standard IP for Android Virtual Machine
        // (1) // Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2/laundryRush/api/").addConverterFactory(GsonConverterFactory.create()).build();


        // 192.168.43.229 is the IP address for this machine(laptop) that runs the local server(Xampp - phpmyadmin)
        //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.229/laundryRush/api/").addConverterFactory(GsonConverterFactory.create()).build();

        // (2) // RequestInterface request = retrofit.create(RequestInterface.class);

        // the parameters need to be the same input as in RequesInterface.java with 'register' object
        // (3) // Call<JSONResponse> call = request.register(user_email, user_name, password);

        //Call<JSONResponse> call = requestInterface.register(user_email, user_name, password);
        Call<User> call = requestInterface.register(user_email, user_name, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                //if(response.body().getResponse().equals("Success"))
                //if(response.isSuccessful())
                if(response.body().getResponse() == 1){

                    //String username = response.body().getUsername();

                    //hiding progress dialog
                    //progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                    // shared preferences try out
 /*                   //if there is no error
                    //if (!response.body().getError()) {
                        //starting profile activity
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser()); // getUser
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //} */

                    // go to login activity class once registered success
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    //MainActivity.prefConfig.displayToast("Successfully Registered!");

                    //preferenceConfig.displayToast("Registered Successfully!");
                    //->not working, might delete PreferenceConfig.java
                    //and pref_file,pref_login_status and pref_user_name in strings.xml

                    //JSONResponse responseSuccess = response.body();
                    //Toast.makeText(getApplicationContext(), responseSuccess.getResponse().toString(), Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "Register Successfully",Toast.LENGTH_LONG).show();

                }else {
                    //progressDialog.dismiss();

                    Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

                /*else if(response.body().getResponse().equals("Missing Parameter")){



                }else if(response.body().getResponse().equals("Email Exist")){



                }else if(response.body().getResponse().equals("Username Exist")){



                }else if(response.body().getResponse().equals("Error")){



                }*/
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                //toast "Error" when register user, but user details are registered successfully into database

                Toast.makeText(RegisterActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });

/*        txtEmail.setText("");
        txtUsernameRegister.setText("");
        txtPasswordRegister.setText("");
        txtConfirmPassword.setText("");*/




    }

/*    public void shiftToLoginActivity(View view){
        Intent intent_shiftLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent_shiftLogin);
    }*/

}
