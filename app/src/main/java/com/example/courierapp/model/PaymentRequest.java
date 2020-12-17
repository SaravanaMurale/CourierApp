package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest {

    @SerializedName("detail")
    private String paymentDetail;
    @SerializedName("amount")
    private int paymentAmount;
    @SerializedName("order_id")
    private String paymentOrderId;
    @SerializedName("name")
    private String paymentUserName;
    @SerializedName("email")
    private String paymentUserEmail;
    @SerializedName("phone")
    private String paymentUserMobile;


    public PaymentRequest(String paymentDetail, int paymentAmount, String paymentOrderId, String paymentUserName, String paymentUserEmail, String paymentUserMobile) {
        this.paymentDetail = paymentDetail;
        this.paymentAmount = paymentAmount;
        this.paymentOrderId = paymentOrderId;
        this.paymentUserName = paymentUserName;
        this.paymentUserEmail = paymentUserEmail;
        this.paymentUserMobile = paymentUserMobile;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public String getPaymentUserName() {
        return paymentUserName;
    }

    public String getPaymentUserEmail() {
        return paymentUserEmail;
    }

    public String getPaymentUserMobile() {
        return paymentUserMobile;
    }
}
