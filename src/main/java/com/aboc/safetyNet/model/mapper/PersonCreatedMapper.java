package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonCreatedDTO;

public class PersonCreatedMapper {

    public PersonCreatedMapper() {
    }

    //Convertit un DTO en entité Person (utilisé lors de l'ajout)
    public static Person toEntity(PersonCreatedDTO personCreatedDTO) {
        Person person = new Person();

        person.setFirstName(personCreatedDTO.getFirstName());
        person.setLastName(personCreatedDTO.getLastName());
        person.setAddress(personCreatedDTO.getAddress());
        person.setCity(personCreatedDTO.getCity());
        person.setZip(personCreatedDTO.getZip());
        person.setPhone(personCreatedDTO.getPhone());
        person.setEmail(personCreatedDTO.getEmail());

        return person;
    }

    //Convertit une entité person en personDTO (utilisé lors de la réponse API)
    public static PersonCreatedDTO toDto(Person person) {
        PersonCreatedDTO personCreatedDTO = new PersonCreatedDTO();

        personCreatedDTO.setFirstName(person.getFirstName());
        personCreatedDTO.setLastName(person.getLastName());
        personCreatedDTO.setAddress(person.getAddress());
        personCreatedDTO.setCity(person.getCity());
        personCreatedDTO.setZip(person.getZip());
        personCreatedDTO.setPhone(person.getPhone());
        personCreatedDTO.setEmail(person.getEmail());

        return personCreatedDTO;
    }
}
