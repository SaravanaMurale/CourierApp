package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMyAllShipmentDTO {

    @SerializedName("ShipperscontactName")
    private String senderName;
    @SerializedName("ShipperscontactNumber")
    private String senderMobileNum;
    @SerializedName("ReceivercontactName")
    private String receiverName;
    @SerializedName("ReceivercontactNumber")
    private String receiverMobileNum;
    @SerializedName("trackno")
    private String accountNumber;
    @SerializedName("ServiceItem")
    private String productType;

    @SerializedName("weight")
    private String weight;

    @SerializedName("delivery_date")
    private String date;

    @SerializedName("status")
    private String trackingStatus;


    /*@SerializedName("")
    private String fromLat;
    @SerializedName("")
    private String fromLongi;

    @SerializedName("")
    private String deliveryLat;
    @SerializedName("")
    private String deliveryLongi;

    private String fromFullAddress;
    private String toFullAddress;
*/

    @SerializedName("instruction")
    private String instruction;


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDate() {
        return date;
    }

    /*public String getFromLat() {
        return fromLat;
    }

    public void setFromLat(String fromLat) {
        this.fromLat = fromLat;
    }

    public String getFromLongi() {
        return fromLongi;
    }

    public void setFromLongi(String fromLongi) {
        this.fromLongi = fromLongi;
    }

    public String getDeliveryLat() {
        return deliveryLat;
    }

    public void setDeliveryLat(String deliveryLat) {
        this.deliveryLat = deliveryLat;
    }

    public String getDeliveryLongi() {
        return deliveryLongi;
    }

    public void setDeliveryLongi(String deliveryLongi) {
        this.deliveryLongi = deliveryLongi;
    }

    public String getFromFullAddress() {
        return fromFullAddress;
    }

    public void setFromFullAddress(String fromFullAddress) {
        this.fromFullAddress = fromFullAddress;
    }

    public String getToFullAddress() {
        return toFullAddress;
    }

    public void setToFullAddress(String toFullAddress) {
        this.toFullAddress = toFullAddress;
    }*/

    public String getInstruction() {
        return instruction;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }
}
