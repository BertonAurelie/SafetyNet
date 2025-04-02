package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.FirePersonInfoResponse;

public class FirePersonInfoMapper {

    public FirePersonInfoMapper(){}

    public static Person toEntity(FirePersonInfoResponse firePersonInfoResponse){
        Person person = new Person();

        person.setLastName(firePersonInfoResponse.getLastName());
        person.setPhone(firePersonInfoResponse.getPhone());

        return person;
    }

    public static FirePersonInfoResponse toDto(Person person){
        FirePersonInfoResponse firePersonInfoResponse = null;

        if(person != null){
            firePersonInfoResponse = new FirePersonInfoResponse();

            firePersonInfoResponse.setLastName(person.getLastName());
            firePersonInfoResponse.setPhone(person.getPhone());
        }

        return firePersonInfoResponse;
    }
}
