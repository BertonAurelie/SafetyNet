package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.FamilyMemberResponse;
import com.aboc.safetyNet.model.dto.response.ChildWithFamilyResponse;

import java.util.List;

public class TargetChildMapper {

    public TargetChildMapper(){}

    public static Person toEntity(ChildWithFamilyResponse childWithFamilyResponse){
        Person person = new Person();

        person.setFirstName(childWithFamilyResponse.getFirstName());
        person.setLastName(childWithFamilyResponse.getLastName());

        return person;
    }

    public static ChildWithFamilyResponse toDto(Person person, long age, List<FamilyMemberResponse> familyMembers){
        ChildWithFamilyResponse childWithFamilyResponse = null;

        if(person != null){
            childWithFamilyResponse = new ChildWithFamilyResponse();
            childWithFamilyResponse.setFirstName(person.getFirstName());
            childWithFamilyResponse.setLastName(person.getLastName());
            childWithFamilyResponse.setAge(age);
            childWithFamilyResponse.setFamilyMember(familyMembers);
        }
        return childWithFamilyResponse;
    }
}
