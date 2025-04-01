package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.FamilyMemberResponse;

public class ChildAlertMapper {

    public ChildAlertMapper(){}

    public static Person toEntity(FamilyMemberResponse familyMemberResponse){
        Person person = new Person();

        person.setFirstName(familyMemberResponse.getFirstName());
        person.setLastName(familyMemberResponse.getLastName());

        return person;
    }

    public static FamilyMemberResponse toDto(Person person){
        FamilyMemberResponse familyMemberResponse = null;

        if(person != null){
            familyMemberResponse = new FamilyMemberResponse();

            familyMemberResponse.setFirstName(person.getFirstName());
            familyMemberResponse.setLastName(person.getLastName());
        }

        return familyMemberResponse;
    }
}
