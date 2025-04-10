package com.aboc.safetyNet;

import com.aboc.safetyNet.model.dto.request.PersonCreatedDTO;
import com.aboc.safetyNet.model.dto.request.PersonUpdatedDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllPersonsTest() throws Exception {
        this.mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void addPersonTest() throws Exception {
        PersonCreatedDTO addedPerson = new PersonCreatedDTO();
        addedPerson.setFirstName("testFirstName1");
        addedPerson.setLastName("testLastName1");
        addedPerson.setAddress("test1");
        addedPerson.setCity("test1");
        addedPerson.setZip(3);
        addedPerson.setEmail("test1@mail.com");
        addedPerson.setPhone("test1");

        this.mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addedPerson)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatedPersonTest() throws Exception {
        PersonUpdatedDto updatedDto = new PersonUpdatedDto();
        updatedDto.setFirstName("testFirstName1");
        updatedDto.setLastName("testLastName1");
        updatedDto.setZip(2);

        this.mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePersonTest() throws Exception {
        addPersonTest();

        mockMvc.perform(delete("/person")
                        .param("firstName", "testFirstName1")
                        .param("lastName", "testLastName1"))
                .andExpect(status().isOk());
    }

}
