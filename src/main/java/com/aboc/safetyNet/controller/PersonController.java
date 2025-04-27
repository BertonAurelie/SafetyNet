package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.request.PersonCreatedDTO;
import com.aboc.safetyNet.model.dto.request.PersonUpdatedDto;
import com.aboc.safetyNet.service.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) throws IOException {
        this.personService = personService;
        logger.info("loading PersonController");
    }

    /**
     * Retrieves all persons from the database.
     *
     * @return ResponseEntity containing a list of all Person
     */
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.info("Executing getAllPersons");
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    /**
     * Adds a new person to the database.
     *
     * @param person (PersonCreatedDTO) containing the new person's details
     * @return ResponseEntity containing the created person (PersonCreatedDTO)
     * @throws IOException if saving the new person fails
     */
    @PostMapping
    public ResponseEntity<PersonCreatedDTO> addPerson(@RequestBody @Valid PersonCreatedDTO person) throws IOException {
        return new ResponseEntity<>(personService.addNewPerson(person), HttpStatus.CREATED);
    }

    /**
     * Deletes an existing person identified by their first and last name.
     *
     * @param firstName of person to delete
     * @param lastName  of person to delete
     * @return ResponseEntity indicating the outcome (success or not found)
     * @throws IOException if deletion fails
     */
    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        Boolean delete = personService.deletePerson(firstName, lastName);
        if (delete) {
            return new ResponseEntity<>("person successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Person to delete not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Updates an existing person with new details.
     *
     * @param person (PersonUpdatedDto) containing updated information
     * @return ResponseEntity containing the updated PersonUpdatedDto
     * @throws IOException if update fails
     */
    @PutMapping
    public ResponseEntity<PersonUpdatedDto> updatePerson(@RequestBody PersonUpdatedDto person) throws IOException {
        return new ResponseEntity<>(personService.editPerson(person), HttpStatus.OK);
    }
}