package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonDTO;
import com.aboc.safetyNet.model.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public Person editDataPerson(Person person) throws IOException {
        Person personUpdated = null;
        if (person.getFirstName() != null && person.getLastName() != null) {
            for (Person personDB : persons) {
                if (person.equals(personDB)) {
                    if (StringUtils.hasText(person.getAddress())) {
                        personDB.setAddress(person.getAddress());
                        logger.info("Address modified");
                    }
                    if (StringUtils.hasText(person.getCity())) {
                        personDB.setCity(person.getCity());
                        logger.info("City modified");
                    }
                    if (person.getZip() != null) {
                        personDB.setZip(person.getZip());
                        logger.info("Zip modified");
                    }
                    if (StringUtils.hasText(person.getPhone())) {
                        personDB.setPhone(person.getPhone());
                        logger.info("Phone number modified");
                    }
                    if (StringUtils.hasText(person.getEmail())) {
                        personDB.setEmail(person.getEmail());
                        logger.info("email modified");
                    }
                    personUpdated = personDB;
                    break;
                }
            }
        }
        if(personUpdated != null) {
            dataService.writeData();
            logger.info("change saved successfully");
        }
        return personUpdated;
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
