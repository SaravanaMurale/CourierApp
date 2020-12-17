package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class PaymentUserResponseDTO {

    @SerializedName("Username")
    private String paymentUserName;
    @SerializedName("ReceivercontactNumber")
    private String paymentUserContactNum;
    @SerializedName("Receiveremail")
    private String paymentUserEmail;
    @SerializedName("trackno")
    private String paymentUserTrackNo;
    @SerializedName("price")
    private int paymentUserPrice;

    public PaymentUserResponseDTO(String paymentUserName, String paymentUserContactNum, String paymentUserEmail, String paymentUserTrackNo, int paymentUserPrice) {
        this.paymentUserName = paymentUserName;
        this.paymentUserContactNum = paymentUserContactNum;
        this.paymentUserEmail = paymentUserEmail;
        this.paymentUserTrackNo = paymentUserTrackNo;
        this.paymentUserPrice = paymentUserPrice;
    }

    public String getPaymentUserName() {
        return paymentUserName;
    }

    public void setPaymentUserName(String paymentUserName) {
        this.paymentUserName = paymentUserName;
    }

    public String getPaymentUserContactNum() {
        return paymentUserContactNum;
    }

    public void setPaymentUserContactNum(String paymentUserContactNum) {
        this.paymentUserContactNum = paymentUserContactNum;
    }

    public String getPaymentUserEmail() {
        return paymentUserEmail;
    }

    public void setPaymentUserEmail(String paymentUserEmail) {
        this.paymentUserEmail = paymentUserEmail;
    }

    public String getPaymentUserTrackNo() {
        return paymentUserTrackNo;
    }

    public void setPaymentUserTrackNo(String paymentUserTrackNo) {
        this.paymentUserTrackNo = paymentUserTrackNo;
    }

    public int getPaymentUserPrice() {
        return paymentUserPrice;
    }

    public void setPaymentUserPrice(int paymentUserPrice) {
        this.paymentUserPrice = paymentUserPrice;
    }
}
