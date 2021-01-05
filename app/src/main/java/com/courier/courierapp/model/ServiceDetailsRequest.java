package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class ServiceDetailsRequest {

    @SerializedName("name")
    String shipperServiceName;
    @SerializedName("item")
    String shipperItem;
    @SerializedName("weight")
    String shipperWeight;


    public ServiceDetailsRequest(String shipperServiceName, String shipperItem, String shipperWeight) {
        this.shipperServiceName = shipperServiceName;
        this.shipperItem = shipperItem;
        this.shipperWeight = shipperWeight;
    }

    public String getShipperServiceName() {
        return shipperServiceName;
    }

    public String getShipperItem() {
        return shipperItem;
    }

    public String getShipperWeight() {
        return shipperWeight;
    }
}
