package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController// bean + retour méthode au format JSON dans le corps de la réponse HTTP.
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) throws IOException {
        this.personService = personService;
        logger.info("loading PersonController");
    }

    /**
     * Read - Get all persons
     *
     * @return - An Iterable object of Persons full filled
     */
    @GetMapping
    public List<Person> getAllPersons() {
        logger.info("loading getAllPersons");
        return personService.getAllPersons();
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws IOException {
        return new ResponseEntity<Person>(personService.addNewPerson(person), HttpStatus.CREATED);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        personService.deletePerson(firstName, lastName);
    }

}