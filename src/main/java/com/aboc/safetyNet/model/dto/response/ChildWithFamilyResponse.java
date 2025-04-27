package com.aboc.safetyNet.model.dto.response;

import java.util.List;
/**
 * used for the DTO ChildAlertResponse.
 * retrieves information of child(firstName, lastName, age) & a list of him family member.
 */
public class ChildWithFamilyResponse {
    private String firstName;
    private String lastName;
    private long age;
    private List<FamilyMemberResponse> familyMember;

    //Constructors
    public ChildWithFamilyResponse() {
    }

    public ChildWithFamilyResponse(String firstName, String lastName, long age, List<FamilyMemberResponse> familyMember) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.familyMember = familyMember;
    }

    //Getter & Setter
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

    public List<FamilyMemberResponse> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<FamilyMemberResponse> familyMember) {
        this.familyMember = familyMember;
    }
}
