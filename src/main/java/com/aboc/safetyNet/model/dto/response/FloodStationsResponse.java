package com.aboc.safetyNet.model.dto.response;

import java.util.List;

/**
 * DTO representing list of persons living at specific address.
 * uses floodHouseResponse as attribute.
 * used for flood method of SafetyNetService.
 */
public class FloodStationsResponse {
    private List<FloodHouseResponse> floodHouse;

    //Constructor
    public FloodStationsResponse() {
    }

    public FloodStationsResponse(List<FloodHouseResponse> floodHouse) {
        this.floodHouse = floodHouse;
    }

    //Getter & Setter
    public List<FloodHouseResponse> getFloodHouse() {
        return floodHouse;
    }

    public void setFloodHouse(List<FloodHouseResponse> floodHouse) {
        this.floodHouse = floodHouse;
    }
}

