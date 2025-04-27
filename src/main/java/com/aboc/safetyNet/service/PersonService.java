package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.request.PersonCreatedDTO;
import com.aboc.safetyNet.model.dto.request.PersonUpdatedDto;
import com.aboc.safetyNet.model.mapper.PersonCreatedMapper;
import com.aboc.safetyNet.model.mapper.PersonUpdatedMapper;
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

    /**
     * GET
     * Retrieve the full list of persons from data file.
     *
     * @return a list of {@link Person}
     */
    public List<Person> getAllPersons() {
        logger.info("Loading list of all persons.");
        return persons;
    }

    /**
     * Add a new person to the list and save it in the data file.
     *
     * @param personCreatedDto
     * @return the person added
     * @throws IOException                  if the data can't be saved
     * @throws SafetyNetBadRequestException if the person is incomplete
     */
    public PersonCreatedDTO addNewPerson(PersonCreatedDTO personCreatedDto) throws IOException {
        Person person = PersonCreatedMapper.toEntity(personCreatedDto);
        logger.info("Attempting to add person: {}", person);

        if (person != null) {
            persons.add(person);
            dataService.writeData();
            logger.info("Person successfully added.");
            return PersonCreatedMapper.toDto(person);
        } else {
            logger.warn("Invalid person data received.");
            throw new SafetyNetBadRequestException("Person should be full");
        }
    }

    /**
     * Update an existing person’s details.
     * Matches by first and last name.
     *
     * @param personUpdatedDto the person data with updated fields
     * @return the updated person
     * @throws IOException if the data cannot be saved
     */
    public PersonUpdatedDto editPerson(PersonUpdatedDto personUpdatedDto) throws IOException {
        Person person = PersonUpdatedMapper.toEntity(personUpdatedDto);
        Person personUpdated = null;
        if (StringUtils.hasText(person.getFirstName()) && StringUtils.hasText(person.getLastName())) {
            for (Person personDB : persons) {
                if (person.equals(personDB)) {
                    // Update each field if the new value is present
                    if (StringUtils.hasText(person.getAddress())) {
                        personDB.setAddress(person.getAddress());
                        logger.info("Address updated");
                    }
                    if (StringUtils.hasText(person.getCity())) {
                        personDB.setCity(person.getCity());
                        logger.info("City updated");
                    }
                    if (person.getZip() != null) {
                        personDB.setZip(person.getZip());
                        logger.info("Zip updated");
                    }
                    if (StringUtils.hasText(person.getPhone())) {
                        personDB.setPhone(person.getPhone());
                        logger.info("Phone number updated");
                    }
                    if (StringUtils.hasText(person.getEmail())) {
                        personDB.setEmail(person.getEmail());
                        logger.info("email updated");
                    }
                    personUpdated = personDB;
                    break;
                }
            }
        }
        if (personUpdated != null) {
            dataService.writeData();
            logger.info("change saved successfully");
        }
        return PersonUpdatedMapper.toDto(person);
    }

    /**
     * DELETE
     * Delete a person identified by first name and last name.
     *
     * @param firstNameX the first name of the person
     * @param lastNameY  the last name of the person
     * @return true if the person was found and deleted, false otherwise
     * @throws IOException if the data can't be deleted
     */
    public Boolean deletePerson(String firstNameX, String lastNameY) throws IOException {
        boolean found = false;
        if (firstNameX != null && lastNameY != null) {
            logger.info("Searching for person to delete: {} {}", firstNameX, lastNameY);
            for (Person personDb : persons) {
                if (firstNameX.equals(personDb.getFirstName()) && lastNameY.equals(personDb.getLastName())) {
                    persons.remove(personDb);
                    dataService.writeData();
                    logger.info("person successfully deleted");
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            logger.warn("Person not found: {} {}", firstNameX, lastNameY);
        }

        return found;
    }
}
