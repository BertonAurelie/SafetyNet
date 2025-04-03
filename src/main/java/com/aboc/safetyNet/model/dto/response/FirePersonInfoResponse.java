package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class FirePersonInfoResponse {
    private String lastName;
    private String phone;
    private Long age;
    private List<String> medicalRecord;
    private List<String> allergies;

    public FirePersonInfoResponse() {
    }



    public FirePersonInfoResponse(String lastName, String phone, Long age, List<String> medicalRecord, List<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medicalRecord = medicalRecord;
        this.allergies = allergies;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public List<String> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<String> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

}
