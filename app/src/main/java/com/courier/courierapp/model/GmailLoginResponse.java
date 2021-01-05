package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class GmailLoginResponse {

    @SerializedName("status")
    boolean gmailUserStatus;

    public boolean isGmailUserStatus() {
        return gmailUserStatus;
    }
}
