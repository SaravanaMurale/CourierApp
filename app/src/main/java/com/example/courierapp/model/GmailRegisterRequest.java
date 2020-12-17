package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class GmailRegisterRequest {

    @SerializedName("email")
    String gmailId;
    @SerializedName("name")
    String gmailName;
    @SerializedName("password")
    String gmailPassword;


    public GmailRegisterRequest(String gmailId, String gmailName, String gmailPassword) {
        this.gmailId = gmailId;
        this.gmailName = gmailName;
        this.gmailPassword = gmailPassword;
    }

    public GmailRegisterRequest(String gmailId, String gmailPassword) {
        this.gmailId = gmailId;
        this.gmailPassword = gmailPassword;
    }

    public String getGmailId() {
        return gmailId;
    }

    public String getGmailName() {
        return gmailName;
    }

    public String getGmailPassword() {
        return gmailPassword;
    }
}
