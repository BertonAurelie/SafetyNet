package com.aboc.safetyNet;

import com.aboc.safetyNet.model.Data;
import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.*;
import com.aboc.safetyNet.service.DataService;
import com.aboc.safetyNet.service.SafetyNetService;
import org.junit.jupiter.api.Assertions;
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
public class SafetyNetServiceTest {
    @Mock
    private Data data;

    @Mock
    private DataService dataService;

    private SafetyNetService safetyNetService;

    @BeforeEach
    public void setUp() throws IOException {
        Firestation firestation1 = new Firestation("firestation1", 1);
        Firestation firestation2 = new Firestation("firestation2", 2);
        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation1);
        firestationList.add(firestation2);

        Person adult1 = new Person("adult1", "adult1", "firestation1", "city1", 123, "adult1", "adult1");
        Person adult2 = new Person("adult2", "adult1", "firestation1", "city1", 123, "adult2", "adult2");
        Person adult3 = new Person("adult3", "adult3", "firestation2", "city2", 123, "adult3", "adult3");
        Person child1 = new Person("child1", "adult1", "firestation1", "city1", 123, "child1", "child1");
        Person child2 = new Person("child2", "adult1", "firestation1", "city1", 123, "child2", "child2");
        Person child3 = new Person("child3", "adult3", "firestation2", "city2", 123, "child3", "child3");
        List<Person> personList = new ArrayList<>();
        personList.add(adult1);
        personList.add(adult2);
        personList.add(adult3);
        personList.add(child1);
        personList.add(child2);
        personList.add(child3);

        MedicalRecord medicalRecordAdult1 = new MedicalRecord("adult1", "adult1", "10/10/1991");
        MedicalRecord medicalRecordAdult2 = new MedicalRecord("adult2", "adult1", "10/10/1992");
        MedicalRecord medicalRecordAdult3 = new MedicalRecord("adult3", "adult3", "10/10/1993");
        MedicalRecord medicalRecordChild1 = new MedicalRecord("child1", "adult1", "01/01/2020");
        MedicalRecord medicalRecordChild2 = new MedicalRecord("child2", "adult1", "01/01/2022");
        MedicalRecord medicalRecordChild3 = new MedicalRecord("child3", "adult3", "01/12/2007");
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecordAdult1);
        medicalRecordList.add(medicalRecordAdult2);
        medicalRecordList.add(medicalRecordAdult3);
        medicalRecordList.add(medicalRecordChild1);
        medicalRecordList.add(medicalRecordChild2);
        medicalRecordList.add(medicalRecordChild3);

        List<String> medications = new ArrayList<>();
        medications.add("medication1");

        List<String> allergies = new ArrayList<>();
        allergies.add("allergie1");
        allergies.add("allergie2");
        medicalRecordAdult3.setAllergies(allergies);
        medicalRecordAdult3.setMedications(medications);


        Data fakeData = new Data();
        fakeData.setPersons(personList);
        fakeData.setFirestations(firestationList);
        fakeData.setMedicalrecords(medicalRecordList);


        when(dataService.loadFileData()).thenReturn(fakeData);
        safetyNetService = new SafetyNetService(dataService);
    }

    @Test
    public void givenStationNumberOfFirestation_whenfoundPersonWithStationNumberOfFirestation_thenReturnListOfPersonsAndCountOfAdultsAndChildren() {

        FirestationCoverageResponse result = safetyNetService.foundPersonWithStationNumberOfFirestation(1);

        assertEquals(4, result.getPersons().size());

        assertEquals(2, result.getAdultCount());
        assertEquals(2, result.getChildrenCount());

        // Vérifie les noms des personnes
        List<String> names = result.getPersons().stream()
                .map(PersonCoveredByStationResponse::getFirstName)
                .toList();

        Assertions.assertTrue(names.contains("adult1"));
        Assertions.assertTrue(names.contains("child2"));
    }

    @Test
    public void givenAddress_whenGetChildrenAtAddress_thenReturnListOfChildrenAtThisAddressAndTheirFamily() {
        ChildAlertResponse result = safetyNetService.getChildrenAtAddress("firestation1");
        assertEquals(2, result.getChildren().size());
        assertEquals("child1", result.getChildren().get(0).getFirstName());
        assertEquals("adult1", result.getChildren().get(0).getLastName());
        assertEquals(5, result.getChildren().get(0).getAge());
        assertEquals(3, result.getChildren().get(0).getFamilyMember().size());

        assertEquals("adult1", result.getChildren().get(0).getFamilyMember().get(0).getFirstName());
        assertEquals("adult1", result.getChildren().get(0).getFamilyMember().get(0).getLastName());
        assertEquals(33, result.getChildren().get(0).getFamilyMember().get(0).getAge());

    }

    @Test
    public void givenStationNumber_whenPhoneAlert_thenReturnPhoneList() {
        PhoneAlertResponse result = safetyNetService.phoneAlert(1);

        assertEquals(4, result.getPhoneList().size());
    }

    @Test
    public void givenAddress_whenFire_thenReturnListOfPersonsAndStationNumberWithThisAddress() {
        FireAddressResponse result = safetyNetService.fire("firestation1");

        assertEquals(4, result.getFirePersonInfoResponse().size());
        assertEquals("adult1", result.getFirePersonInfoResponse().get(0).getLastName());
        assertEquals("adult1", result.getFirePersonInfoResponse().get(0).getPhone());
        assertEquals(33, result.getFirePersonInfoResponse().get(0).getAge());
        assertEquals(0, result.getFirePersonInfoResponse().get(0).getMedicalRecord().size());
        assertEquals(0, result.getFirePersonInfoResponse().get(0).getAllergies().size());

        assertEquals(1, result.getStation());
    }

    @Test
    public void givenListOfStationNumbers_whenFlood_thenReturnListOfPersonsWithSameAddressOfStationNumber() {
        List<Integer> stations = new ArrayList<>();
        stations.add(1);
        stations.add(2);

        FloodStationsResponse result = safetyNetService.flood(stations);

        assertEquals(2, result.getFloodHouse().size());
        assertEquals(4, result.getFloodHouse().get(0).getPersonsAtThisAddress().size());
        assertEquals(2, result.getFloodHouse().get(1).getPersonsAtThisAddress().size());
        assertEquals("firestation1", result.getFloodHouse().get(0).getAddress());
    }

    @Test
    public void givenLastName_whenGetPersons_thenReturnMatchingPersonsWithMedicalInfo() {
        List<PersonInfoResponse> result = safetyNetService.getPersonsByLastName("adult3");

        assertEquals(2, result.size());
        assertEquals("adult3", result.get(0).getLastName());
        assertEquals("firestation2", result.get(0).getAddress());
        assertEquals(31, result.get(0).getAge());
        assertEquals("adult3", result.get(0).getEmail());
        assertEquals(1, result.get(0).getMedications().size());
        assertEquals(2, result.get(0).getAllergies().size());
    }

    @Test
    public void givenCity_whenGetEmailsByCity_thenReturnListEmailsPersontoThisCity() {
        List<String> result = safetyNetService.getEmailsByCity("city1");

        assertEquals(4, result.size());
    }

}
