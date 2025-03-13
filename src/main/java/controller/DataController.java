package controller;

import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.DataService;

import java.util.List;

@RestController // bean + retour méthode au format JSON dans le corps de la réponse HTTP.
public class DataController {
    @Autowired
    private DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Read - Get all persons
     * @return - An Iterable object of Persons full filled
     */
    @GetMapping("/persons")
    public List<Person> getEmployees() {
        return dataService.getAllPersons();
    }
}