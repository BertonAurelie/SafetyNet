package com.aboc.safetyNet.model.dto.response;


/**
 * Dto representing person covered by specific firestation.
 * Retrieve information of this person( firstName, lastName, address and phone number).
 * Used for foundPersonWithStationNumberOfFirestation method of SafetyNetService.
 */
public class PersonCoveredByStationResponse {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonCoveredByStationResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
