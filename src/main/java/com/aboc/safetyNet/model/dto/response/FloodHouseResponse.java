package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class FloodHouseResponse {
    private String address;
    List<FirePersonInfoResponse> personsAtThisAddress;

    public FloodHouseResponse() {
    }

    public FloodHouseResponse(String address, List<FirePersonInfoResponse> personsAtThisAddress) {
        this.address = address;
        this.personsAtThisAddress = personsAtThisAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FirePersonInfoResponse> getPersonsAtThisAddress() {
        return personsAtThisAddress;
    }

    public void setPersonsAtThisAddress(List<FirePersonInfoResponse> personsAtThisAddress) {
        this.personsAtThisAddress = personsAtThisAddress;
    }
}
