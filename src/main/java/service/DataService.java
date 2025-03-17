package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import model.Data;
import model.Firestation;
import model.MedicalRecord;
import model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private Data data;
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public DataService(){}

    @PostConstruct
    public void init() throws IOException {
        loadFileData();
    }

    public Data loadFileData() throws IOException {
        logger.info("Chargement des données JSON...");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File dataJsonFile = new File("src/main/resources/data.json");
            data = objectMapper.readValue(dataJsonFile, Data.class);
            // Code de chargement
            logger.info("Données chargées avec succès : {} personnes chargées", data.getPersons().size());
        } catch (Exception e) {
            logger.error("Erreur lors du chargement du fichier JSON", e);
            data = new Data(); // Initialisation avec un objet vide pour éviter les NullPointerException
        }
        return data;
    }

    public List<Person> getAllPersons() {
        if (data == null || data.getPersons() == null) {
            logger.warn("Aucune donnée disponible !");
            return List.of(); // Retourne une liste vide au lieu de null
        }
        return data.getPersons();
    }

    public Person foundPerson(String firstName) {
        if (data == null || data.getPersons().isEmpty()) {
            logger.warn("Aucune donnée chargée !");
            return null;
        }

        return data.getPersons().stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    public void editDataPersons(String x){
        for(int i = 0; i < data.getPersons().size(); i++){
            data.showInfo();
        }
    }
}

