package com.aboc.safetyNet.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class PersonUpdatedDto {
    @NotNull(message = "firstName may not be empty")
    private String firstName;

    @NotBlank(message = "lastName may not be empty")
    private String lastName;

    private String address;

    private String city;

    private Integer zip;

    private String phone;

    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    //Constructor
    public PersonUpdatedDto() {
    }

    //GETTER / SETTER


    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public Integer getZip() {return zip;}
    public void setZip(Integer zip) {this.zip = zip;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}



}
