package com.aboc.safetyNet;

import com.aboc.safetyNet.model.Data;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.dto.request.MedicalRecordDto;
import com.aboc.safetyNet.service.DataService;
import com.aboc.safetyNet.service.FirestationService;
import com.aboc.safetyNet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private Data data;
    @Mock
    private DataService dataService;

    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setup() throws IOException {
        Data fakeData = new Data();
        MedicalRecord fakeMedicalRecord = new MedicalRecord("test", "test", "test");
        List<String> medications = new ArrayList<>();
        medications.add("medications1");
        List<MedicalRecord> fakeListMedicalRecord = new ArrayList<>();
        fakeListMedicalRecord.add(fakeMedicalRecord);
        fakeData.setMedicalrecords(fakeListMedicalRecord);

        when(dataService.loadFileData()).thenReturn(fakeData);
        medicalRecordService = new MedicalRecordService(dataService);
    }

    @Test
    public void getAllMedications(){
        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecord();

        assertEquals(1, result.size());
    }

    @Test
    public void addNewMedicalRecord() throws IOException {
        MedicalRecordDto addMedical = new MedicalRecordDto();
        addMedical.setFirstName("addFirstName");
        addMedical.setLastName("addLastName");
        addMedical.setBirthdate("17/17/1777");
        medicalRecordService.addNewMedicalRecord(addMedical);

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecord();

        assertEquals(2, result.size());
        assertEquals("addFirstName", result.get(1).getFirstName());
    }

    @Test
    public void updatedMedicalRecord()throws  IOException{
        List<String> updatedList = new ArrayList<>();
        updatedList.add("add");
        MedicalRecordDto updatedMedical = new MedicalRecordDto();
        updatedMedical.setFirstName("test");
        updatedMedical.setLastName("test");
        updatedMedical.setBirthdate("updated");
        updatedMedical.setMedications(updatedList);
        updatedMedical.setAllergies(updatedList);

        medicalRecordService.updatedMedicalRecord(updatedMedical);

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecord();
        assertEquals(1,result.size());
        assertEquals("updated", result.get(0).getBirthdate());
        assertEquals(updatedList, result.get(0).getMedications());
        assertEquals(updatedList,result.get(0).getAllergies());
    }

    @Test
    public void deleteMedicalRecord() throws IOException{
        medicalRecordService.deleteMedicalRecord("test","test");

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecord();
        assertEquals(0, result.size());
    }

    @Test
    public void unknownMedicalRecordToDelete() throws IOException{
        medicalRecordService.deleteMedicalRecord("unknown", "unknown");

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecord();
        assertEquals(1, result.size());
    }

}
