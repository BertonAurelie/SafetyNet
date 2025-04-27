package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.request.PersonUpdatedDto;

/**
 * mapper personUpdatedDto to Person & person to PersonUpdatedDto
 * used for PUT status of personService(editPerson method).
 */
public class PersonUpdatedMapper {

    public PersonUpdatedMapper() {
    }

    //Convertir un DTO en entité Person (utilisé lors de l'ajout)
    public static Person toEntity(PersonUpdatedDto personUpdatedDto) {
        Person person = new Person();

        person.setFirstName(personUpdatedDto.getFirstName());
        person.setLastName(personUpdatedDto.getLastName());
        person.setAddress(personUpdatedDto.getAddress());
        person.setCity(personUpdatedDto.getCity());
        person.setZip(personUpdatedDto.getZip());
        person.setPhone(personUpdatedDto.getPhone());
        person.setEmail(personUpdatedDto.getEmail());

        return person;
    }

    //Convertir une entité person en personDTO (utilisé lors de la réponse API)
    public static PersonUpdatedDto toDto(Person person) {
        PersonUpdatedDto personUpdatedDto = new PersonUpdatedDto();

        personUpdatedDto.setFirstName(person.getFirstName());
        personUpdatedDto.setLastName(person.getLastName());
        personUpdatedDto.setAddress(person.getAddress());
        personUpdatedDto.setCity(person.getCity());
        personUpdatedDto.setZip(person.getZip());
        personUpdatedDto.setPhone(person.getPhone());
        personUpdatedDto.setEmail(person.getEmail());

        return personUpdatedDto;
    }
}
