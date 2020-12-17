package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class Legs {

    @SerializedName("distance")
    private Distance distance;
    //private Duration duration;
   // private String end_address;
   // private Location end_location;
   // private String start_address;
    //private Location start_location;
   // private List<Steps> steps;
    //private List<?> traffic_speed_entry;
    //private List<?> via_waypoint;


    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}

