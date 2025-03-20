package com.aboc.safetyNet;


import com.aboc.safetyNet.controller.PersonController;
import com.aboc.safetyNet.model.Data;
import com.aboc.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.aboc.safetyNet.service.DataService;
import com.aboc.safetyNet.service.PersonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private Data mockData; // On mock la classe Data

    @Mock
    private DataService dataService;

    private PersonService personService;

    private Data data;
    private DataService dataService2;




    @BeforeEach
    public void setup() throws IOException {
        Data fakeData = new Data();
        List<Person> fakePerson = new ArrayList<>();
        fakePerson.add(new Person("testFirstName", "testLastName", "test", "test", 123, "test", "test"));
        fakeData.setPersons(fakePerson);
       when(dataService.loadFileData()).thenReturn(fakeData);
        personService = new PersonService(dataService);
    }

    @Test
    public void getAllProductsTest() {
        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testFirstName", result.get(0).getFirstName());
    }

    @Test
    public void testGetAllPersons() {
        personService.getAllPersons();
    }

    @Test
    public void addNewDataTest() throws IOException {
        Person addThisPerson = new Person("essaiFirstName", "essaiLastName", "essaiAddress", "essaiCity", 123, "essaiPhone", "essaimail");
        personService.addNewPerson(addThisPerson);

        List<Person> result = personService.getAllPersons();

        assertEquals(2, result.size());
    }

    @Test
    public void editDataObjectTest() throws IOException {
        personService.editDataPerson("testFirstName", "testLastName", null, null, null, null, "testEmail");

        List<Person> result = personService.getAllPersons();

        assertEquals(1, result.size());
        assertEquals("testEmail", result.get(0).getEmail());
    }

    @Test
    public void deleteDataObjectTest() throws IOException {
        personService.deletePerson("testFirstName", "testLastName");
        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

