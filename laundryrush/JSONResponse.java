package com.example.laundryrush;

import com.google.gson.annotations.SerializedName;

public class JSONResponse {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

/*    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "JSONResponse{" +
                "response='" + response + '\'' +
                '}';
    }*/
}
