package com.aboc.safetyNet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MedicalRecordDto {
    @NotBlank(message="firstName may not be empty")
    private String firstName;

    @NotBlank(message ="lastName may not be empty")
    private String lastName;
    @NotBlank(message="birthdate may not be empty")
    private String birthdate;
    @NotNull(message="medications may not be null")
    private List<String> medications;
    @NotNull(message="allergies may not be null")
    private List<String> allergies;

    public MedicalRecordDto() {}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getBirthdate() {return birthdate;}
    public void setBirthdate(String birthdate) {this.birthdate = birthdate;}

    public List<String> getMedications() {return medications;}
    public void setMedications(List<String> medications) {this.medications = medications;}

    public List<String> getAllergies() {return allergies;}
    public void setAllergies(List<String> allergies) {this.allergies = allergies;}
}
