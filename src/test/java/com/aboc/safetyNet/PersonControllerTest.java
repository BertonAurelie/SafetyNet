package com.aboc.safetyNet;

import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.request.PersonCreatedDTO;
import com.aboc.safetyNet.model.dto.request.PersonUpdatedDto;
import com.aboc.safetyNet.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PersonService personService;

    @Test
    public void givenPersons_whenGetAllPersons_thenReturnPersonsList() throws Exception {
        List<Person> fakePerson = new ArrayList<>();
        fakePerson.add(new Person("testFirstName", "testLastName", "test", "test", 1, "test", "test@mail.com"));
        fakePerson.add(new Person("testFirstName2", "testLastName2", "test2", "test2", 12, "test2", "test2@mail.com"));
        fakePerson.add(new Person("testFirstName3", "testLastName3", "test3", "test3", 123, "test3", "test3@mail.com"));

        when(personService.getAllPersons()).thenReturn(fakePerson);

        this.mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void givenNewPerson_whenAddPerson_thenReturnCreatedPerson() throws Exception {
        PersonCreatedDTO addedPerson = new PersonCreatedDTO();
        addedPerson.setFirstName("testFirstName3");
        addedPerson.setLastName("testLastName3");
        addedPerson.setAddress("test3");
        addedPerson.setCity("test3");
        addedPerson.setZip(3);
        addedPerson.setEmail("test3@mail.com");
        addedPerson.setPhone("test3");

        when(personService.addNewPerson(any())).thenReturn(addedPerson);

        this.mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addedPerson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("testFirstName3"))
                .andExpect(jsonPath("$.zip").value(3));

    }

    @Test
    public void givenPersonToUpdate_whenEditPerson_thenReturnUpdatedPerson() throws Exception {
        PersonUpdatedDto updatedDto = new PersonUpdatedDto();
        updatedDto.setFirstName("testFirstName1");
        updatedDto.setLastName("testLastName1");
        updatedDto.setZip(2);

        when(personService.editPerson(any())).thenReturn(updatedDto);

        this.mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("testFirstName1"))
                .andExpect(jsonPath("$.zip").value(2));
    }

    @Test
    public void givenExistingPerson_whenDeletePerson_thenReturnOk() throws Exception {
        when(personService.deletePerson("testFirstName1", "testLastName1")).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "testFirstName1")
                        .param("lastName", "testLastName1"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNonExistingPerson_whenDeletePerson_thenReturnNotFound() throws Exception {
        when(personService.deletePerson("testFirstName1", "testLastName1")).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .param("firstName", "testFirstName1")
                        .param("lastName", "testLastName1"))
                .andExpect(status().isNotFound());
    }
}

