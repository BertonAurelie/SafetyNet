package com.aboc.safetyNet;

import com.aboc.safetyNet.model.dto.request.FirestationDto;
import com.aboc.safetyNet.service.FirestationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FirestationService firestationService;

    @Test
    public void givenValidFirestationDto_whenAddFirestation_thenReturnCreatedFirestation() throws Exception {
        FirestationDto firestationDto = new FirestationDto();
        firestationDto.setAddress("addAddress");
        firestationDto.setStation(1);

        when(firestationService.addNewFirestation(any())).thenReturn(firestationDto);

        this.mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firestationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("addAddress"))
                .andExpect(jsonPath("$.station").value(1));

    }

    @Test
    public void givenExistingFirestation_whenDeleteFirestation_thenReturnOk() throws Exception {
        when(firestationService.deleteFirestation("deleteAddress", 1)).thenReturn(true);

        this.mockMvc.perform(delete("/firestation")
                        .param("address", "deleteAddress")
                        .param("station", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNonExistingFirestation_whenDeleteFirestation_thenReturnNotFound() throws Exception {
        when(firestationService.deleteFirestation("deleteAddress", 1)).thenReturn(false);

        this.mockMvc.perform(delete("/firestation")
                        .param("address", "deleteAddress")
                        .param("station", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidFirestationDto_whenUpdateFirestation_thenReturnUpdatedFirestation() throws Exception {
        FirestationDto firestationDto = new FirestationDto();
        firestationDto.setAddress("address");
        firestationDto.setStation(2);

        when(firestationService.editFirestation(any())).thenReturn(firestationDto);

        this.mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firestationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value(2));
    }
}
