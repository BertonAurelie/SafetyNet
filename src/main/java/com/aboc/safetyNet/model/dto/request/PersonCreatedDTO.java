package com.aboc.safetyNet.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PersonCreatedDTO {

    @NotNull(message = "firstName may not be empty")
    private String firstName;

    @NotBlank(message = "lastName may not be empty")
    private String lastName;

    @NotBlank(message = "address may not be empty")
    private String address;

    @NotBlank(message = "city may not be empty")
    private String city;

    @NotNull(message = "zip may not be empty")
    private Integer zip;

    @NotBlank(message = "phone may not be empty")
    private String phone;

    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    //Constructor
    public PersonCreatedDTO() {
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
