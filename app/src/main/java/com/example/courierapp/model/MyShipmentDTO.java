package com.example.courierapp.model;

public class MyShipmentDTO {

    private int trackingId;
    private int trackingNumber;
    private String Date;

    public MyShipmentDTO(int trackingNumber, String date) {
        this.trackingNumber = trackingNumber;
        Date = date;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
