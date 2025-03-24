package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonDTO;
import com.aboc.safetyNet.model.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private List<Person> persons;
    private DataService dataService;

    public PersonService(DataService dataService) throws IOException {
        this.dataService = dataService;
        persons = dataService.loadFileData().getPersons();
    }

    public List<Person> getAllPersons() {
        System.out.println(persons);
        return persons;
    }

    public Person foundData(String x) {
        for (Person person : persons) {
            if (x.equals(person.getEmail())) {
                logger.info("Person found: " + person.toString());
                return person;
            }
        }
        return null;
    }

    public PersonDTO addNewPerson(PersonDTO personDto) throws IOException {
        Person person = PersonMapper.toEntity(personDto);
        logger.info(person.toString());
        if (person != null) {
            persons.add(person);
            dataService.writeData();
            logger.info("person registered in the database");
            return PersonMapper.toDto(person);
        } else {
            logger.info(person.toString());
            logger.info("disabled person");
            throw new SafetyNetBadRequestException("Person should be full");
        }
    }

    public Person editDataPerson(String firstName, String lastName, String newAddress, String newCity, Integer newZip, String newPhone, String newEmail) throws IOException {
        if (firstName != null && lastName != null) {
            for (Person person : persons) {
                if (firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                    if (newAddress != null) {
                        person.setAddress(newAddress);
                        logger.info("Address modified");
                    }
                    if (newCity != null) {
                        person.setCity(newCity);
                        logger.info("City modified");
                    }
                    if (newZip != null) {
                        person.setZip(newZip);
                        logger.info("Zip modified");
                    }
                    if (newPhone != null) {
                        person.setPhone(newPhone);
                        logger.info("Phone number modified");
                    }
                    if (newEmail != null) {
                        person.setEmail(newEmail);
                        logger.info("email modified");
                    }
                }
                dataService.writeData();
                logger.info("change saved successfully");
                return person;
            }
        }
        return null;
    }

    public Boolean deletePerson(String firstNameX, String lastNameY) throws IOException {
        boolean found = false;
        if (firstNameX != null && lastNameY != null) {
            logger.info("search for person to delete.");
            for (Person person : persons) {
                if (firstNameX.equals(person.getFirstName()) && lastNameY.equals(person.getLastName())) {
                    persons.remove(person);
                    dataService.writeData();
                    logger.info("person successfully deleted");
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
