package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMyAllShipmentResponse {

    @SerializedName("data")
    List<GetMyAllShipmentDTO> getMyAllShipmentDTOList;

    public GetMyAllShipmentResponse(List<GetMyAllShipmentDTO> getMyAllShipmentDTOList) {
        this.getMyAllShipmentDTOList = getMyAllShipmentDTOList;
    }

    public List<GetMyAllShipmentDTO> getGetMyAllShipmentDTOList() {
        return getMyAllShipmentDTOList;
    }

    public void setGetMyAllShipmentDTOList(List<GetMyAllShipmentDTO> getMyAllShipmentDTOList) {
        this.getMyAllShipmentDTOList = getMyAllShipmentDTOList;
    }

}
