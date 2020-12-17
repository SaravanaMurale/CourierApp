package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class SchedulePickupResponse {

    @SerializedName("transactionstatus")
    String transactionStatus;
    @SerializedName("trackno")
    String trackingNo;
    @SerializedName("message")
    String trackingMsg;

    public SchedulePickupResponse(String transactionStatus, String trackingNo, String trackingMsg) {
        this.transactionStatus = transactionStatus;
        this.trackingNo = trackingNo;
        this.trackingMsg = trackingMsg;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public void setTrackingMsg(String trackingMsg) {
        this.trackingMsg = trackingMsg;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public String getTrackingMsg() {
        return trackingMsg;
    }
}
