package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryrush.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etCurrentPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnUpdatePassword;

    private User user;
    public static RequestInterface requestInterface;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_password);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);

        user = new User();

        btnUpdatePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnUpdatePassword:
                doUpdatePassword();
                break;
        }

    }

    private void doUpdatePassword() {

/*        user.setEmail(txtEmail.getText().toString().trim());
        user.setUsername(txtUsernameRegister.getText().toString().trim());
        user.setPassword(txtPasswordRegister.getText().toString().trim());

        String user_email = user.getEmail();
        String user_name = user.getUsername();
        String password = user.getPassword();*/

        String curr_password = etCurrentPassword.getText().toString().trim();
        String new_password = etNewPassword.getText().toString().trim();
        String confirm_password = etConfirmPassword.getText().toString().trim();

        sessionManagement = new SessionManagement(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = sessionManagement.getUserDetails();

        String sp_username = user.get(SessionManagement.KEY_NAME);

        Call<User> call = requestInterface.updatePassword(sp_username, curr_password, new_password, confirm_password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body().getResponse() == 1){

                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ManagePasswordActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(ManagePasswordActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
