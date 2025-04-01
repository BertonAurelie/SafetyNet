package com.aboc.safetyNet.model.dto.response;

public class FamilyMemberResponse {
    private String firstName;
    private String lastName;
    private long age;

    public FamilyMemberResponse(){}

    public FamilyMemberResponse(String firstName, String lastName, long age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
