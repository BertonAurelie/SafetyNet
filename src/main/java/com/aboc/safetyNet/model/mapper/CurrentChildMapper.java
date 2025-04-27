package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.ChildWithFamilyResponse;
import com.aboc.safetyNet.model.dto.response.FamilyMemberResponse;

import java.util.List;

/**
 * mapper ChildWithFamilyResponse to Person & person to ChildWithFamilyResponse
 * used for getChildrenAtAddress method of safetyNetService.
 */
public class CurrentChildMapper {

    public CurrentChildMapper() {
    }

    public static Person toEntity(ChildWithFamilyResponse childWithFamilyResponse) {
        Person person = new Person();

        person.setFirstName(childWithFamilyResponse.getFirstName());
        person.setLastName(childWithFamilyResponse.getLastName());

        return person;
    }

    public static ChildWithFamilyResponse toDto(Person person, long age, List<FamilyMemberResponse> familyMembers) {
        ChildWithFamilyResponse childWithFamilyResponse = null;

        if (person != null) {
            childWithFamilyResponse = new ChildWithFamilyResponse();
            childWithFamilyResponse.setFirstName(person.getFirstName());
            childWithFamilyResponse.setLastName(person.getLastName());
            childWithFamilyResponse.setAge(age);
            childWithFamilyResponse.setFamilyMember(familyMembers);
        }
        return childWithFamilyResponse;
    }
}
