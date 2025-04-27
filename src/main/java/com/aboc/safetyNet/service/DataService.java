package com.aboc.safetyNet.service;

import com.aboc.safetyNet.model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DataService {
    private static Data dataList = null;
    File filePathData = new File("src/main/resources/data.json");
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public DataService() {
    }

    public Data loadFileData() throws IOException {
        logger.info("{}Loading data JSON...{}", "");
        if (dataList == null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                dataList = objectMapper.readValue(filePathData, Data.class);
                logger.debug("Data recovery : ");

            } catch (Exception e) {
                logger.debug("file could not be loaded correctly");
            }
        }
        logger.info(dataList.toString());
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

