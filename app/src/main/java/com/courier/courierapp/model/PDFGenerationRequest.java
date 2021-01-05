package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class PDFGenerationRequest {

    @SerializedName("userid")
    private int userId;
    @SerializedName("trackno")
    private String trackNum;

    public PDFGenerationRequest(int userId, String trackNum) {
        this.userId = userId;
        this.trackNum = trackNum;
    }

    public int getUserId() {
        return userId;
    }

    public String getTrackNum() {
        return trackNum;
    }
}
