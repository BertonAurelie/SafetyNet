package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class FireAddressResponse {
    private Integer station;
    private List<FirePersonInfoResponse> firePersonInfoResponse;

    public FireAddressResponse() {
    }

    public FireAddressResponse(Integer station, List<FirePersonInfoResponse> firePersonInfoResponse) {
        this.station = station;
        this.firePersonInfoResponse = firePersonInfoResponse;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public List<FirePersonInfoResponse> getFirePersonInfoResponse() {
        return firePersonInfoResponse;
    }

    public void setFirePersonInfoResponse(List<FirePersonInfoResponse> firePersonInfoResponse) {
        this.firePersonInfoResponse = firePersonInfoResponse;
    }
}
