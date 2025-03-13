package com.ABoc.safetyNet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.DataService;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DataService dataService;

    @BeforeEach
    void setUp() throws IOException {
        String testDataFile = "src/test/resources/dataTest.json";
        File fileDataTest = new File(testDataFile);

        ObjectMapper objectMapper = new ObjectMapper();
        Data dataTest = objectMapper.readValue(fileDataTest, Data.class);

        dataService = new DataService();
    }


    @Test
    public void foundPersonTest(){
        dataService.foundPerson("Lily");
    }

}
