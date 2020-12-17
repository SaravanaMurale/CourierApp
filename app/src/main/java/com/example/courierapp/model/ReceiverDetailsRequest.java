package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class ReceiverDetailsRequest {

    @SerializedName("companyName")
    String shipperCompanyName;
    @SerializedName("address")
    String shipperAddress;
    @SerializedName("contactName")
    String shipperContactName;
    @SerializedName("contactNumber")
    String shipperContactNumber;
    @SerializedName("email")
    String shipperContactEmail;
    @SerializedName("latitude")
    String shipperLatitue;
    @SerializedName("longitude")
    String shipperLongitude;

    public ReceiverDetailsRequest(String shipperCompanyName, String shipperAddress, String shipperContactName, String shipperContactNumber, String shipperContactEmail, String shipperLatitue, String shipperLongitude) {
        this.shipperCompanyName = shipperCompanyName;
        this.shipperAddress = shipperAddress;
        this.shipperContactName = shipperContactName;
        this.shipperContactNumber = shipperContactNumber;
        this.shipperContactEmail = shipperContactEmail;
        this.shipperLatitue = shipperLatitue;
        this.shipperLongitude = shipperLongitude;
    }

    public String getShipperCompanyName() {
        return shipperCompanyName;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public String getShipperContactName() {
        return shipperContactName;
    }

    public String getShipperContactNumber() {
        return shipperContactNumber;
    }

    public String getShipperContactEmail() {
        return shipperContactEmail;
    }

    public String getShipperLatitue() {
        return shipperLatitue;
    }

    public String getShipperLongitude() {
        return shipperLongitude;
    }
}
