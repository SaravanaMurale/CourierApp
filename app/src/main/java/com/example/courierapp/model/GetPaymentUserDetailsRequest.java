package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class GetPaymentUserDetailsRequest {

    @SerializedName("userid")
    int userId;
    @SerializedName("trackno")
    String trackNo;

    public GetPaymentUserDetailsRequest(int userId, String trackNo) {
        this.userId = userId;
        this.trackNo = trackNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }
}
