package service;

import model.Person;
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

    public void addNewPerson(Person person) throws IOException {
        if (person != null) {
            persons.add(person);
            dataService.writeData();
            logger.info("person registered in the database");
        } else {
            logger.info("disabled person");
        }
    }

    public void editDataPerson(String firstName, String lastName, String newAddress, String newCity, Integer newZip, String newPhone, String newEmail) throws IOException {
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
                    if(newEmail != null){
                        person.setEmail(newEmail);
                        logger.info("email modified");
                    }
                }
            }
            dataService.writeData();
            logger.info("change saved successfully");
        }
    }

    public void deleteDataPerson(String firstNameX, String lastNameY) throws IOException {
        if (firstNameX != null && lastNameY != null) {
            logger.info("search for person to delete.");
            for (Person person : persons) {
                if (firstNameX.equals(person.getFirstName()) && lastNameY.equals(person.getLastName())) {
                    persons.remove(person);
                    dataService.writeData();
                    break;
                } else {
                    logger.info("no people to delete .");
                }
            }
        }
    }
}
