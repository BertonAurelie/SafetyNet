package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class FirestationCoverageResponse {
    List<PersonCoveredByStationResponse> persons;
    int adultCount;
    int childrenCount;

    public FirestationCoverageResponse(){}

    public FirestationCoverageResponse(List<PersonCoveredByStationResponse> persons, int adultCount, int childrenCount) {
        this.persons = persons;
        this.adultCount = adultCount;
        this.childrenCount = childrenCount;
    }

    public List<PersonCoveredByStationResponse> getPersons() {return persons;}
    public void setPersons(List<PersonCoveredByStationResponse> persons) {this.persons = persons;}

    public int getAdultCount() {return adultCount;}
    public void setAdultCount(int adultCount) {this.adultCount = adultCount;}

    public int getChildrenCount() {return childrenCount;}
    public void setChildrenCount(int childrenCount) {this.childrenCount = childrenCount;}
}
