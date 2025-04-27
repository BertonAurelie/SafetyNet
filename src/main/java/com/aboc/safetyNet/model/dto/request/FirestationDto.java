package com.aboc.safetyNet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for transferring firestation information during HTTP requests
 * Used for creating or updating a firestation mapping (POST and PUT status).
 * Attributes station and address can't be empty
 */
public class FirestationDto {
    @NotBlank(message = "address may not be empty")
    private String address;

    @NotNull(message = "station may not be empty")
    private Integer station;

    //Constructor
    public FirestationDto() {
    }

    //Getter et Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
