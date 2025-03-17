package com.ABoc.safetyNet;


import com.fasterxml.jackson.databind.ObjectMapper;
import model.Data;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.DataService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {
    private DataService dataService; // Ne pas mocker, on teste un vrai objet
    private Data dataTest;

    @BeforeEach
    void setUp() throws IOException {
        String testDataFile = "src/test/resources/dataTest.json";
        File fileDataTest = new File(testDataFile);

        ObjectMapper objectMapper = new ObjectMapper();
        dataTest = objectMapper.readValue(fileDataTest, Data.class);

        // Créer une instance réelle de DataService avec des données test
        dataService = new DataService();
        dataService.loadFileData();
    }

    @Test
    public void foundPersonTest() throws IOException {
        List<Person> persons = dataService.getAllPersons();
        assertNotNull(persons); // Vérifier que la liste n'est pas vide
        assertFalse(persons.isEmpty()); // Vérifier qu'il y a des personnes
        System.out.println("Personnes trouvées : " + persons.size());

        dataService.foundPerson("CHARGEMENT");
    }


}
