package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsApiResponseDTO {

    /*private String status;
    private List<GeocodedWayPoints> geocodedWaypoints;*/
    @SerializedName("routes")
    private List<Routes> routes;

   /* public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodedWayPoints> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWayPoints> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }*/

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }
}
