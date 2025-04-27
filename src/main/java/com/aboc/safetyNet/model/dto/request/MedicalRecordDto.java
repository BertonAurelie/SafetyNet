package com.aboc.safetyNet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
/**
 * DTO for transferring medicalRecord information during HTTP requests
 * Used for creating or updating a medicalRecord mapping (POST and PUT status).
 * All Attributes can't be empty
 */
public class MedicalRecordDto {
    @NotBlank(message = "firstName may not be empty")
    private String firstName;
    @NotBlank(message = "lastName may not be empty")
    private String lastName;
    @NotBlank(message = "birthdate may not be empty")
    private String birthdate;
    @NotNull(message = "medications may not be null")
    private List<String> medications;
    @NotNull(message = "allergies may not be null")
    private List<String> allergies;

    //Constructor
    public MedicalRecordDto() {
    }

    //Getter et Setter
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
