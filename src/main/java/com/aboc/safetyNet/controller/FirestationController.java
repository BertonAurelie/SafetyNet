package com.aboc.safetyNet.controller;


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

@RestController
@RequestMapping("/firestation")
public class FirestationController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationController.class);
    private final FirestationService firestationService;
    private final SafetyNetService safetyNetService;

    public FirestationController(FirestationService firestationService, SafetyNetService safetyNetService) {
        this.firestationService = firestationService;
        this.safetyNetService = safetyNetService;
        logger.info("loading FirestationController");
    }


    /**
     * Adds a new firestation mapping.
     *
     * @param firestation the firestation data to add
     * @return the added firestation data with HTTP 201 Created status
     * @throws IOException if an error occurs during the operation
     */
    @PostMapping
    public ResponseEntity<FirestationDto> addFirestation(@RequestBody @Valid FirestationDto firestation) throws IOException {
        return new ResponseEntity<>(firestationService.addNewFirestation(firestation), HttpStatus.CREATED);
    }

    /**
     * Deletes an existing firestation mapping based on address and station number.
     *
     * @param address the address of the firestation
     * @param station the station number of the firestation
     * @return HTTP 200 OK if deleted successfully, or HTTP 404 Not Found if no match
     * @throws IOException if an error occurs during the operation
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFirestation(@RequestParam String address, @RequestParam Integer station) throws IOException {
        Boolean delete = firestationService.deleteFirestation(address, station);
        if (delete) {
            return new ResponseEntity<>("firestation successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("firestation to delete not found", HttpStatus.NOT_FOUND);
    }

    /**
     * updates an existing firestation mapping.
     *
     * @param firestation the updated firestation data
     * @return the updated firestation data with HTTP 200 OK, or HTTP 409 Conflict if update fails
     * @throws IOException if an error occurs during the operation
     */
    @PutMapping
    public ResponseEntity<FirestationDto> updateFirestation(@RequestBody FirestationDto firestation) throws IOException {
        FirestationDto firestationDto = firestationService.editFirestation(firestation);

        if (firestationDto != null) {
            return new ResponseEntity<>(firestationDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(firestationDto, HttpStatus.CONFLICT);
    }

    /**
     * Retrieves persons covered by a firestation given its station number.
     *
     * @param station the firestation number
     * @return a {@link FirestationCoverageResponse} containing the list of persons and statistics
     */
    @GetMapping
    public ResponseEntity<FirestationCoverageResponse> getPersonWithStationNumber(@RequestParam Integer station) {
        return new ResponseEntity<>(safetyNetService.foundPersonWithStationNumberOfFirestation(station), HttpStatus.OK);
    }
}
