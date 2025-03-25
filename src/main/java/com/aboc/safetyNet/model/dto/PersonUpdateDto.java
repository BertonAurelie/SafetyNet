package com.aboc.safetyNet.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonUpdateDto {

    private String address;

    private String city;

    private Integer zip;

    private String phone;

    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    //Constructor
    public PersonUpdateDto() {
    }

    //GETTER / SETTER
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
