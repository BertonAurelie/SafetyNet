package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Data;
import model.Firestation;
import model.MedicalRecord;
import model.Person;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private Data data;

    public DataService(){ try {
        loadFileData();
    } catch (IOException e) {
        System.out.println("erreur");
    }
    }

    public Data loadFileData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File dataJsonFile = new File("src/main/resources/data.json");
        data = objectMapper.readValue(dataJsonFile, Data.class);
        return data;
    }

    public List<Person> getAllPersons(){
            return data.getPersons();
    }

    public void foundPerson(String x){
        if(data != null) {
            for (Person person : data.getPersons()) {
                if (x.equals(person.getFirstName())) {
                    System.out.println("personnne trouvée" + person.getFirstName() + " " + person.getLastName());
                }
            }
        }

    }

    public void editDataPersons(String x){
        for(int i = 0; i < data.getPersons().size(); i++){
            data.showInfo();
        }
    }
}

