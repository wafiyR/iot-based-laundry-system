package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryrush.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lblChangePassword;
    private EditText etviewUsername;
    private EditText etviewEmail;
    private Button btnUpdate;
    private TextView lblprofileUsername;
    private TextView lblprofileEmail;

    private SessionManagement sessionManagement;

    private User userModel;

    public static RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);

        etviewUsername = findViewById(R.id.etviewUsername);
        etviewEmail = findViewById(R.id.etviewEmail);
        btnUpdate = findViewById(R.id.btnUpdateProfile);
        lblChangePassword = findViewById(R.id.lblprofileChangePassword);
        lblprofileUsername = findViewById(R.id.lblprofileUsername);
        lblprofileEmail = findViewById(R.id.lblprofileEmail);
        
        displayDetails();

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

        //Toast.makeText(getApplicationContext(), sp_username,Toast.LENGTH_LONG).show();

        userModel = new User();

        btnUpdate.setOnClickListener(this);
        lblChangePassword.setOnClickListener(this);
    }

    private void displayDetails() {

        // userModel.setUsername(etviewUsername.getText().toString().trim());

       // final String user_name = userModel.getUsername();

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        final HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

       /* if(user_name != null){
            // need to check if user update/change their username, as once username is changed in database, username in database != username of sharedperferences when user firstly log in, this query in fetch_profile.php has error
        } */

        Call<List<User>> call = requestInterface.getProfile(sp_username);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                List<User> userDetails = response.body();

                String username = userDetails.get(0).getUsername();
                String email = userDetails.get(0).getEmail();

                lblprofileUsername.setText(username);
                lblprofileEmail.setText(email);



            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Toast.makeText(UserProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.lblprofileChangePassword:
                startActivity(new Intent(this, ManagePasswordActivity.class));
                break;

            case R.id.btnUpdateProfile:
                doUpdateProfile();
                break;
        }

    }

    private void doUpdateProfile() {

        userModel.setUsername(etviewUsername.getText().toString().trim());
        userModel.setEmail(etviewEmail.getText().toString().trim());

        final String user_name = userModel.getUsername();
        String user_email = userModel.getEmail();

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);


        if(user_name != null && user_email.isEmpty()){  // !user_name.isEmpty()

            // if user_name has input, but user_email is empty

            Call<User> call = requestInterface.updateUsername(sp_username, user_name);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if(response.body().getResponse() == 1){

                        Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                        // update username in sharedpreferences when user update/change their username

                        sessionManagement.updateSPUsername(user_name);

                        Intent reloadActivity = new Intent();
                        reloadActivity = getIntent();

                        finish();

                        startActivity(reloadActivity);

                    }else{
                        Toast.makeText(UserProfileActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Toast.makeText(UserProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            });


        }else if(user_name.isEmpty() && user_email != null){ // !user_email.isEmpty()

            // if user_name is empty, but user_email has input

            Call<User> call = requestInterface.updateEmail(sp_username, user_email);

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
                        Toast.makeText(UserProfileActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Toast.makeText(UserProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        } else if(user_name != null && user_email != null){ // !user_name.isEmpty() && !user_email.isEmpty()

            // if user_name and user_email have input
            Call<User> call = requestInterface.updateProfile(sp_username, user_name, user_email);

            // update username in sharedpreferences when user update/change their username

            //sessionManagement.updateSPUsername(user_name);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if(response.body().getResponse() == 1){

                        Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                        // update username in sharedpreferences when user update/change their username

                        // sessionManagement.updateSPUsername(user_name);

                        // update username in sharedpreferences when user update/change their username

                        sessionManagement.updateSPUsername(user_name);

                        Intent reloadActivity = new Intent();
                        reloadActivity = getIntent();

                        finish();

                        startActivity(reloadActivity);

                        // update username in sharedpreferences when user update/change their username

                        // sessionManagement.updateSPUsername(user_name);

                        // if etviewUsername != null, (user has input to update username)
                        // edit shared preferences for username

                        // need to update ui(text username and text email) once change occur, like refresh/relauch activity
                        // need to edit/update sharedpreferences once change occur

                    }else{
                        Toast.makeText(UserProfileActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UserProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_delete, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch(item.getItemId()){

            case R.id.delete_acc:

                sessionManagement = new SessionManagement(getApplicationContext());

                // get user data from session
                HashMap<String, String> user = sessionManagement.getUserDetails();

                String sp_username = user.get(SessionManagement.KEY_NAME);

                Call<User> call = requestInterface.deleteAccount(sp_username);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                        sessionManagement.editor.clear().apply();
                        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                        finish();

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                        Toast.makeText(UserProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
