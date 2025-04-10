package com.aboc.safetyNet;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Data;
import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.dto.request.FirestationDto;
import com.aboc.safetyNet.service.DataService;
import com.aboc.safetyNet.service.FirestationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
    @Mock
    private Data mockData; // On mock la classe Data

    @Mock
    private DataService dataService;

    private FirestationService firestationService;

    @BeforeEach
    public void setup() throws IOException {
        Data fakeData = new Data();
        List<Firestation> fakeFirestation = new ArrayList<>();
        fakeFirestation.add(new Firestation("testFirestation", 2));
        fakeData.setFirestations(fakeFirestation);
        when(dataService.loadFileData()).thenReturn(fakeData);
        firestationService = new FirestationService(dataService);
    }

    @Test
    public void getAllFirestationsTest() {
        List<Firestation> result = firestationService.getAllFirestation();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testFirestation", result.get(0).getAddress());
    }

    @Test
    public void updateFirestationTest() throws IOException {
        FirestationDto updatedFirestation = new FirestationDto();
        updatedFirestation.setAddress("testFirestation");
        updatedFirestation.setStation(1);

        firestationService.editFirestation(updatedFirestation);

        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(1, result.get(0).getStation());
    }

    @Test
    public void noUpdatedFirestationTest() throws IOException {
        FirestationDto firestation = new FirestationDto();
        firestation.setAddress("testFirestation");

        SafetyNetBadRequestException exception = Assertions.assertThrows(
                SafetyNetBadRequestException.class,
                () -> firestationService.editFirestation(firestation)
        );

        Assertions.assertEquals("firestation should be full", exception.getMessage());

    }

    @Test
    public void unknownFirestationToUpdateTest() throws IOException {
        FirestationDto unknownFirestation = new FirestationDto();
        unknownFirestation.setAddress("invalidFirestation");
        unknownFirestation.setStation(2);

        firestationService.editFirestation(unknownFirestation);
        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(1, result.size());
    }

    @Test
    public void addNewFirestationTest() throws IOException {
        FirestationDto firestation = new FirestationDto();
        firestation.setAddress("newFirestation");
        firestation.setStation(3);

        firestationService.addNewFirestation(firestation);
        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(2, result.size());
        assertEquals("newFirestation", result.get(1).getAddress());

    }

    @Test
    public void deleteFirestationTest() throws IOException {
        firestationService.deleteFirestation("testFirestation",2);

        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(0, result.size());

    }

    @Test
    public void noDeleteFirestationTest() throws IOException {
        assertFalse(firestationService.deleteFirestation("unknownFirestation",2));

        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(1, result.size());
    }
}
