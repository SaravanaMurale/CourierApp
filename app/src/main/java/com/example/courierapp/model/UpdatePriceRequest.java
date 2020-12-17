package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class UpdatePriceRequest {

    @SerializedName("trackno")
    String trackNumber;
    @SerializedName("price")
    int price;

    public UpdatePriceRequest(String trackNumber, int price) {
        this.trackNumber = trackNumber;
        this.price = price;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
