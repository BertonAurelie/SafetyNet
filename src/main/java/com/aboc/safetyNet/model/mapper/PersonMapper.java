package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonDTO;

public class PersonMapper {

    public PersonMapper(){};

    //Convertit un DTO en entité Person (utilisé lors de l'ajout)
    public static Person toEntity(PersonDTO personDTO){
        Person person = new Person();

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAddress(personDTO.getAddress());
        person.setCity(personDTO.getCity());
        person.setZip(personDTO.getZip());
        person.setPhone(personDTO.getPhone());
        person.setEmail(personDTO.getEmail());

        return person;
    };

    //Convertit une entité person en personDTO (utilisé lors de la réponse API)
    public static PersonDTO toDto(Person person){
        PersonDTO personDTO = new PersonDTO();

        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setAddress(person.getAddress());
        personDTO.setCity(person.getCity());
        personDTO.setZip(person.getZip());
        personDTO.setPhone(person.getPhone());
        personDTO.setEmail(person.getEmail());

        return personDTO;
    }
}
