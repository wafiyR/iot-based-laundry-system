package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryrush.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etAdminUsernameLogin;
    private EditText etAdminPasswordLogin;
    private Button btnAdminLogin;

    private User user;

    public static RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);
        user = new User();  // one time, i forgot to do this line, i got error : Attempt to invoke virtual method on a null object reference

        etAdminUsernameLogin = findViewById(R.id.etAdminUsernameLogin);
        etAdminPasswordLogin = findViewById(R.id.etAdminPasswordLogin);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnAdminLogin:

                doAdminLogin();
                break;
        }
    }

    private void doAdminLogin() {

        user.setUsername(etAdminUsernameLogin.getText().toString().trim());
        user.setPassword(etAdminPasswordLogin.getText().toString().trim());


        final String admin_name = user.getUsername(); //declare 'final' cause need to be used in public void onResponse
        final String password = user.getPassword();

        Call<User> call = requestInterface.adminLogin(admin_name, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getResponse() == 1) {

                    startActivity(new Intent(AdminLoginActivity.this, AdminMainMenuActivity.class));

                } else {
                    //hiding progress dialog
                    // progressDialog.dismiss();

                    Toast.makeText(AdminLoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(AdminLoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}