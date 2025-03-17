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
    private static Data dataList = null;
    File filePathData = new File("src/main/resources/data.json");
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public Data loadFileData() throws IOException {
        logger.info("Loading data JSON...");
        if (dataList == null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                dataList = objectMapper.readValue(filePathData, Data.class);
                logger.debug("Data recovery : ");
                System.out.println("Person list : " + dataList.getPersons());
                System.out.println("Firestation list : " + dataList.getFirestations());
                System.out.println("Medicalrecords list : " + dataList.getMedicalrecords());
            } catch (Exception e) {
                logger.debug("file could not be loaded correctly");
            }
        }
        return dataList;
    }

    public void writeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePathData, loadFileData());
            logger.info("file has been modified successfully");
        } catch (Exception e) {
            logger.debug("Unable to edit file.");
        }
    }
}

