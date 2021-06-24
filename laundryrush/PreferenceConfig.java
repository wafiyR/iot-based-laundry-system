package com.example.laundryrush;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PreferenceConfig {

    private final String STATUS = "status";
    private final String USERNAME = "username";

    private SharedPreferences sharedPreferences;
    private Context context;

    // All methods starting with put is used to save information.

    // All methods starting with get is used to get saved information.

    public PreferenceConfig(Context context){

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(STATUS,status);
        // editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }

    public boolean getLoginStatus(){
        //return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),"false");
        return sharedPreferences.getBoolean(STATUS, false);
    }

    public void putUsername(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(context.getString(R.string.pref_user_name),name);
        editor.putString(USERNAME,name);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString(USERNAME,"User");
        //return sharedPreferences.getString(context.getString(R.string.pref_user_name),"User");
    }

    public void displayToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
