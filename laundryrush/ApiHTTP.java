package com.example.laundryrush;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHTTP {

    // public static final String BASE_URL = "http://192.168.43.229/laundryRush/api/";
    public static final String BASE_URL = "https://iot.maiot.academy/api/v1/$ACCESS_TOKEN/rpc/"; // BASE_URL = https://iot.maiot.academy/swagger-ui.html#/     ???    // https://iot.maiot.academy/?
    // baseUrl must end in /        --> (slash or /)

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if(retrofit == null){

            //there is a problem with retrofit in reading json (did not read public void onResponse in call.enqueue)
            // error = Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $
            //to resolve we have to initialize gson object
            //but got other error which is = Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $

            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build(); //add gson in GsonConverterFactory.create()

        }

        return retrofit;

    }
}
