package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //private EditText txtUserNameLogin;
    private EditText txt_usernameLogin;
    private EditText txtPasswordLogin;
    private Button btnLogin;
    private Button btngoRegister;
    //private Button btnAdminLogin;
    private Button btnForgetPassword;

    private ConstraintLayout loginForm;
    //private SharedPreferences sharedPreferences;
    private SessionManagement sessionManagement;

    //private DatabaseHelper databaseHelper;
    private User user;


    public static RequestInterface requestInterface;
    //public static PreferenceConfig preferenceConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestInterface = ApiClient.getApiClient().create(RequestInterface.class);
        //preferenceConfig = new PreferenceConfig(this);

        //txtUserNameLogin = findViewById(R.id.txtUsernameLogin);
        txt_usernameLogin = findViewById(R.id.etUsernameLogin);
        txtPasswordLogin = findViewById(R.id.etUserPasswordLogin);
        btnLogin = findViewById(R.id.btnUserLogin);
        btngoRegister = findViewById(R.id.btngoRegister);
        loginForm = findViewById(R.id.loginform);
        btnForgetPassword = findViewById(R.id.btnForgetPassword);
        //btnAdminLogin = findViewById(R.id.btnGoAdminLogin);



        sessionManagement = new SessionManagement(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManagement.isLoggedIn(), Toast.LENGTH_LONG).show();

/*        sharedPreferences = getSharedPreferences("loginsp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", user_name);
        editor.putString("password", password);
        editor.commit();*/



        // Check if UserResponse is Already Logged In
/*        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            loginForm.setVisibility(View.VISIBLE);
            //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(intent);
        }*/

/*        try {

            btnLogin.setOnClickListener(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        user = new User();  // one time, i forgot to do this line, i got error : Attempt to invoke virtual method on a null object reference

        btnLogin.setOnClickListener(this);
        btngoRegister.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
        //btnAdminLogin.setOnClickListener(this);

        //databaseHelper = new DatabaseHelper(this);

        //initViews();

        // somehow this part of codes cause the app to stop working a.k.a crash

/*        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(txtUserNameLogin.getText().toString(), txtPasswordLogin.getText().toString());
            }
        });*/

/*        btngoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_goRegister);
                finish();
            }
        });*/

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUserLogin:

                //String username = txt_usernameLogin.getText().toString().trim();
                //String password = txtPasswordLogin.getText().toString().trim();

                //doUserLogin(username, password);


                doUserLogin();


                // try if the variable try_username returns null, result = try_username not null, and go to MainActivity.class

/*                String try_username = txt_usernameLogin.getText().toString().trim();

                if(try_username != null){


                    Toast.makeText(this, try_username, Toast.LENGTH_LONG).show(); // when run app, toast the input of user in username text field, means it's not null
                    //startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }else {
                    //Toast.makeText(this, "Username null", Toast.LENGTH_LONG).show();
                    //Log.d("Error", "Username is null");

                    // but why although didn't input any text/string, the if statement is still correct, which means try_username is not null and then go to MainActivity.class
                }*/


/*                //test if btn Login works, and if set LoginActivity.class as the MAIN in AndroidMAnifest.xml works, result = both works

                Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(LoginActivity.this,MainActivity.class));*/


/*                String username = txtUserNameLogin.getText().toString();
                String password = txtPasswordLogin.getText().toString();

                if ("admin".equals(username) && "123".equals(password)) {

                    Intent result = new Intent(username);

                    setResult(RESULT_OK, result);

                    finish();
                } else
                    Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();*/

                break;

            case R.id.btngoRegister:

                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case  R.id.btnForgetPassword:

                //startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;

            /*case R.id.btnGoAdminLogin:

                startActivity(new Intent(this, AdminLoginActivity.class));
                break;*/

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*    public void onBackPressed() {

    }*/

    public void doUserLogin() {

        // why user in declaration (Private User user) is in grey color, even though had been used to call methods? User(model class) not loading properly?

        //String try_username = txt_usernameLogin.getText().toString().trim();

/*        if(try_username != null){
            Toast.makeText(this, "Username filled", Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(this, "Username null", Toast.LENGTH_LONG).show();
            Log.d("Error", "Username is null");
        }*/

        // user.setUsername(txt_usernameLogin.getText().toString().trim());
        //user.setUsername(username);
        //userLogin.setUsername(username);
        // user.setPassword(txtPasswordLogin.getText().toString().trim());
        //userLogin.setPassword(password);

        //defining a progress dialog to show while signing up
/*        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In...");
        progressDialog.show();*/

        user.setUsername(txt_usernameLogin.getText().toString().trim());
        user.setPassword(txtPasswordLogin.getText().toString().trim());



        final String user_name = user.getUsername(); //declare 'final' cause need to be used in public void onResponse
        final String password = user.getPassword();

        Call<User> call = requestInterface.login(user_name, password);

/*        Intent result = new Intent(user_name);

        setResult(RESULT_OK, result);

        finish();*/

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //if(response.body().getResponse().equals("Success"))
                if (response.body().getResponse() == 1) {

                    // String username = response.body().getUsername();

                    // Set Logged In statue to 'true'
                    // SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
/*                    sharedPreferences = getSharedPreferences("loginsp", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("username", user_name);
                    editor.putString("password", password);
                    editor.commit();*/
                    //hiding progress dialog
                    //progressDialog.dismiss();

                    sessionManagement.createLoginSession(user_name, password);

                    //Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                    //Intent intent_login = new Intent(getApplicationContext(), MainActivity.class);


                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    //intent_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TASK);

                    //intent_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    //startActivity(intent_login);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                   /* Intent result = new Intent(user_name);

                    setResult(RESULT_OK, result);

                    finish();*/

                    //goToMainActivity();
                } else {
                    //hiding progress dialog
                   // progressDialog.dismiss();

                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

/*        txt_usernameLogin.setText("");
        txtPasswordLogin.setText("");*/


    }

 /*   public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }*/

/*    public void goToMainActivity(){
        Intent gotoMain = new Intent(this, MainActivity.class);
        startActivity(gotoMain);
    }*/

/*    public void initViews(){

        txtUserNameLogin = findViewById(R.id.txtUsernameLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btngoRegister = findViewById(R.id.btngoRegister);

    }*/

 /*   public void initListeners(){

        btnLogin = setOnClickListener(this);
    }*/

/*    public void onClick(View view){

        switch (view.getId()){
            case R.id.btnLogin:
                loginUser(txtUserNameLogin.getText().toString(), txtPasswordLogin.getText().toString());
                break;

            case R.id.btngoRegister:
                Intent intent_goRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_goRegister);
                break;
        }

    }*/

/*    public void loginUser(String username, String password) {

        if ("admin".equals(username) && "123".equals(password)) {

            Intent result = new Intent(username);

            setResult(RESULT_OK, result);

           // Intent login = new Intent(LoginActivity.this, MainActivity.class);

            //startActivity(login);

            finish();
        } else
            Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();

    }*/




/*    public void onClick(View view) {

        String username = txtUserNameLogin.getText().toString();
        String password = txtPasswordLogin.getText().toString();

        if ("admin".equals(username) && "123".equals(password)) {

            Intent result = new Intent(username);

            setResult(RESULT_OK, result);

            finish();
        } else
            Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();

    }*/

/*    public void shiftToRegisterActivity(View view) {
        Intent intent_shiftRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent_shiftRegister);
    }*/
}
