package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("name")
    String userName;
    @SerializedName("mobile_no")
    String userMobileNumber;
    @SerializedName("password")
    String password;

    public RegisterRequest(String userName, String userMobileNumber, String password) {
        this.userName = userName;
        this.userMobileNumber = userMobileNumber;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public String getPassword() {
        return password;
    }
}
