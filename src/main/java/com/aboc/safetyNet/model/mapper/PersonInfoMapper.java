package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.PersonInfoResponse;

public class PersonInfoMapper {

    public static Person toEntity(PersonInfoResponse personInfoResponse){
        Person person = new Person();

        person.setLastName(personInfoResponse.getLastName());
        person.setAddress(personInfoResponse.getAddress());
        person.setEmail(personInfoResponse.getEmail());

        return person;
    }

    public static PersonInfoResponse toDto(Person person){
        PersonInfoResponse personInfoResponse = null;

        if(person != null){
            personInfoResponse = new PersonInfoResponse();

            personInfoResponse.setLastName(person.getLastName());
            personInfoResponse.setAddress(person.getAddress());
            personInfoResponse.setEmail(person.getEmail());
        }

        return personInfoResponse;
    }
}
