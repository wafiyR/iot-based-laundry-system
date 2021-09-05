package com.example.laundryrush.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("message")
    private String message;

    @SerializedName("deviceToken")
    private String deviceToken;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("response")
    private int response;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getDeviceID() {
        return deviceToken;
    }

    public void setDeviceID(String deviceID) {
        this.deviceToken = deviceID;
    }


}
