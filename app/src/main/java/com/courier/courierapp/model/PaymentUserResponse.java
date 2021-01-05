package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentUserResponse {

    @SerializedName("data")
    List<PaymentUserResponseDTO> paymentUserResponseDTOList;

    public PaymentUserResponse(List<PaymentUserResponseDTO> paymentUserResponseDTOList) {
        this.paymentUserResponseDTOList = paymentUserResponseDTOList;
    }

    public List<PaymentUserResponseDTO> getPaymentUserResponseDTOList() {
        return paymentUserResponseDTOList;
    }

    public void setPaymentUserResponseDTOList(List<PaymentUserResponseDTO> paymentUserResponseDTOList) {
        this.paymentUserResponseDTOList = paymentUserResponseDTOList;
    }
}
