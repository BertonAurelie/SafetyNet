package com.aboc.safetyNet.controller;


import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.dto.request.FirestationDto;
import com.aboc.safetyNet.model.dto.response.FirestationCoverageResponse;
import com.aboc.safetyNet.service.FirestationService;
import com.aboc.safetyNet.service.SafetyNetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FirestationController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationController.class);
    private final FirestationService firestationService;
    private final SafetyNetService safetyNetService;

    public FirestationController(FirestationService firestationService, SafetyNetService safetyNetService){
        this.firestationService = firestationService;
        this.safetyNetService = safetyNetService;
        logger.info("loading FirestationController");
    }

    @PostMapping
    public ResponseEntity<FirestationDto> addFirestation(@RequestBody @Valid FirestationDto firestation) throws IOException {
        return new ResponseEntity<>(firestationService.addNewFirestation(firestation), HttpStatus.CREATED);
    }

    /**
     * Delete -
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFirestation(@RequestParam String adress, @RequestParam Integer station) throws IOException {
        Boolean delete = firestationService.deleteFirestation(adress,station);
        if (delete) {
            return new ResponseEntity<>("firestation successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("firestation to delete not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Edit -
     *
     * @return
     */
    @PutMapping
    public ResponseEntity<FirestationDto> UpdateFirestation(@RequestBody FirestationDto firestation) throws IOException {
        FirestationDto firestationDto = firestationService.editFirestation(firestation);

        if (firestationDto != null) {
            return new ResponseEntity<>(firestationDto, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(firestationDto,  HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<FirestationCoverageResponse> getPersonWithStationNumber(@RequestParam Integer station){
        return new ResponseEntity<>(safetyNetService.foundPersonWithStationNumberOfFirestation(station), HttpStatus.OK);
    }
}
