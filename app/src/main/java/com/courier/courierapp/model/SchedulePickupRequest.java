package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchedulePickupRequest {

    @SerializedName("userid")
    int userId;
    @SerializedName("shipper")
    List<ShipperDetailsRequest> shipperDetailsRequestList;

    @SerializedName("receiver")
    List<ReceiverDetailsRequest> receiverDetailsRequestList;

    @SerializedName("service")
    List<ServiceDetailsRequest> serviceDetailsRequestList;

    @SerializedName("instruction")
    String instruction;
    @SerializedName("delivery_date")
    private String deliveryDate;
    @SerializedName("amount_type")
    private String amtType;


    public SchedulePickupRequest(int userId, List<ShipperDetailsRequest> shipperDetailsRequestList, List<ReceiverDetailsRequest> receiverDetailsRequestList, List<ServiceDetailsRequest> serviceDetailsRequestList, String instruction, String deliveryDate, String amtType) {
        this.userId = userId;
        this.shipperDetailsRequestList = shipperDetailsRequestList;
        this.receiverDetailsRequestList = receiverDetailsRequestList;
        this.serviceDetailsRequestList = serviceDetailsRequestList;
        this.instruction = instruction;
        this.deliveryDate = deliveryDate;
        this.amtType = amtType;
    }

    public SchedulePickupRequest(String instruction, String deliveryDate, String amtType) {
        this.instruction = instruction;
        this.deliveryDate = deliveryDate;
        this.amtType = amtType;
    }

    public int getUserId() {
        return userId;
    }

    public List<ShipperDetailsRequest> getShipperDetailsRequestList() {
        return shipperDetailsRequestList;
    }

    public List<ReceiverDetailsRequest> getReceiverDetailsRequestList() {
        return receiverDetailsRequestList;
    }

    public List<ServiceDetailsRequest> getServiceDetailsRequestList() {
        return serviceDetailsRequestList;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getAmtType() {
        return amtType;
    }
}

