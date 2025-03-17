package controller;

import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PersonService;

import java.util.List;

@RestController // bean + retour méthode au format JSON dans le corps de la réponse HTTP.
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Read - Get all persons
     * @return - An Iterable object of Persons full filled
     */
    @GetMapping("/persons")
    public List<Person> getEmployees() {
        return personService.getAllPersons();
    }
}