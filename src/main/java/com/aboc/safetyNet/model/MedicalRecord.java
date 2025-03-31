package com.aboc.safetyNet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    //Constructeur
    public MedicalRecord(){}

    public MedicalRecord(String firstName, String lastName, String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        medications = new ArrayList();
        allergies = new ArrayList<>();
    }

    //GETTER ET SETTER
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getBirthdate() {return birthdate;}
    public List<String> getMedications() {return medications;}
    public List<String> getAllergies() {return allergies;}

    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setBirthdate(String birthdate) {this.birthdate = birthdate;}
    public void setMedications(List<String> medications) {this.medications = medications;}
    public void setAllergies(List<String> allergies) {this.allergies = allergies;}

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

}
