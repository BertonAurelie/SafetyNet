package com.aboc.safetyNet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the root structure of the data loaded from the JSON file.
 * It contains lists of persons, firestations, and medical records.
 */
public class Data {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

    //Constructor
    public Data() {
        persons = new ArrayList<>();
        firestations = new ArrayList<>();
        medicalrecords = new ArrayList<>();
    }

    //Getter et Setter
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @Override
    public String toString() {
        return "Data{" +
                "persons=" + persons +
                ", firestations=" + firestations +
                ", medicalrecords=" + medicalrecords +
                '}';
    }
}
