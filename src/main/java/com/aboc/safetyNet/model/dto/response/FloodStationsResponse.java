package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class FloodStationsResponse {

    private List<FloodHouseResponse> floodHouse;

    public FloodStationsResponse() {}

    public FloodStationsResponse(List<FloodHouseResponse> floodHouse) {
        this.floodHouse = floodHouse;
    }

    public List<FloodHouseResponse> getFloodHouse() {
        return floodHouse;
    }

    public void setFloodHouse(List<FloodHouseResponse> floodHouse) {
        this.floodHouse = floodHouse;
    }
}

