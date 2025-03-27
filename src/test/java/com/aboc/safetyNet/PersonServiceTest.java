package com.aboc.safetyNet;


import com.aboc.safetyNet.model.Data;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.PersonCreatedDTO;
import com.aboc.safetyNet.model.mapper.PersonCreatedMapper;
import com.aboc.safetyNet.service.DataService;
import com.aboc.safetyNet.service.PersonService;
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
public class PersonServiceTest {
    @Mock
    private Data mockData; // On mock la classe Data

    @Mock
    private DataService dataService;

    private PersonService personService;

    @BeforeEach
    public void setup() throws IOException {
        Data fakeData = new Data();
        List<Person> fakePerson = new ArrayList<>();
        fakePerson.add(new Person("testFirstName", "testLastName", "test", "test", 123, "test", "test@mail.com"));
        fakeData.setPersons(fakePerson);
        when(dataService.loadFileData()).thenReturn(fakeData);
        personService = new PersonService(dataService);
    }

    @Test
    public void getAllPersonsTest() {
        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testFirstName", result.get(0).getFirstName());
    }

    @Test
    public void addNewPersonTest() throws IOException {
        Person person = new Person("new", "new", "new", "new", 123, "new", "new");
        PersonCreatedDTO addThisPerson = PersonCreatedMapper.toDto(person);
        personService.addNewPerson(addThisPerson);

        List<Person> result = personService.getAllPersons();
        assertEquals(2, result.size());
    }
    
   // @Test
    ///public void editDataObjectTest() throws IOException {
        //personService.editDataPerson("testFirstName", "testLastName", null, null, null, null, "testEmail");

    //    List<Person> result = personService.getAllPersons();

    //    assertEquals(1, result.size());
    //    assertEquals("testEmail", result.get(0).getEmail());
   // }

    @Test
    public void deleteDataObjectTest() throws IOException {
        personService.deletePerson("testFirstName", "testLastName");
        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

