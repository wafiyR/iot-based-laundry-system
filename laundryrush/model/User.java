package com.example.laundryrush.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private int userID;
    private String email;
    private String status;

    @SerializedName("password")
    private String password;

    @SerializedName("balance")
    private String eWalletbalance;

    @SerializedName("response")
    private int response; // boolean

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public User(/*int userID,*/ String username, String email /*String gender*/){
        //this.userID = userID;
        this.username = username;
        this.email = email;
        //this.gender = gender;
    }

    public User() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteWalletbalance() {
        return eWalletbalance;
    }

    public void seteWalletbalance(String eWalletbalance) {
        this.eWalletbalance = eWalletbalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }
}
