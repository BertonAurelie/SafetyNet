package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonCreatedDTO;
import com.aboc.safetyNet.model.dto.PersonUpdatedDto;
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
     * Read - Get all persons on DB
     *
     * @return - An Iterable object of Persons full filled
     */
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.info("loading getAllPersons");
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    /**
     * Create - add new person on DB
     *
     * @return this person
     */
    @PostMapping
    public ResponseEntity<PersonCreatedDTO> addPerson(@RequestBody @Valid PersonCreatedDTO person) throws IOException {
        return new ResponseEntity<>(personService.addNewPerson(person), HttpStatus.CREATED);
    }

    /**
     * Delete - Delete person on DB
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
     * Edit - Edit person
     *
     * @return person updated
     */
    @PutMapping
    public ResponseEntity<PersonUpdatedDto> UpdatePerson(@RequestBody PersonUpdatedDto person) throws IOException {
        return new ResponseEntity<>(personService.editPerson(person), HttpStatus.NO_CONTENT);
    }
}