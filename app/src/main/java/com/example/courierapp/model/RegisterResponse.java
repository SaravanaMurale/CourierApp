package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
