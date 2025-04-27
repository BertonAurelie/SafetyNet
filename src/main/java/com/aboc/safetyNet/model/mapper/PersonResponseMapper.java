package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.PersonCoveredByStationResponse;

/**
 * Mapper PersonCoveredByStationResponse to person & person to PersonCoveredByStationResponse.
 * Used for foundPersonWithStationNumberOfFirestation method of SafetyNetService.
 */
public class PersonResponseMapper {

    public PersonResponseMapper() {
    }

    //Convertit un DTO en entité Person (utilisé lors de l'ajout)
    public static Person toEntity(PersonCoveredByStationResponse personCoveredByStationResponse) {
        Person person = new Person();

        person.setFirstName(personCoveredByStationResponse.getFirstName());
        person.setLastName(personCoveredByStationResponse.getLastName());
        person.setAddress(personCoveredByStationResponse.getAddress());
        person.setPhone(personCoveredByStationResponse.getPhone());

        return person;
    }

    //Convertit une entité person en personDTO (utilisé lors de la réponse API)
    public static PersonCoveredByStationResponse toDto(Person person) {
        PersonCoveredByStationResponse personCoveredByStationResponse = new PersonCoveredByStationResponse();

        personCoveredByStationResponse.setFirstName(person.getFirstName());
        personCoveredByStationResponse.setLastName(person.getLastName());
        personCoveredByStationResponse.setAddress(person.getAddress());
        personCoveredByStationResponse.setPhone(person.getPhone());

        return personCoveredByStationResponse;
    }
}
