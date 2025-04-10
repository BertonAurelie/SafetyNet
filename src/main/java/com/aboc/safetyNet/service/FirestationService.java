package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.dto.request.FirestationDto;
import com.aboc.safetyNet.model.mapper.FirestationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class FirestationService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    private List<Firestation> firestations;
    private DataService dataService;

    public FirestationService(DataService dataService) throws IOException {
        this.dataService = dataService;
        firestations = dataService.loadFileData().getFirestations();
    }

    /**
     * Retrieve the full list of firestation from data file.
     * @return a list of {@Link Firestation}
     */
    public List<Firestation> getAllFirestation() {
        logger.info("Loading list of all firestation.");
        return firestations;
    }


    /**
     * Add new firestation to the list and save it to the data file.
     * @param firestationDto
     * @return the firestation added
     * @throws IOException if the data can't be saved
     * @throws SafetyNetBadRequestException if the firestation is incomplete
     */
    public FirestationDto addNewFirestation(FirestationDto firestationDto) throws IOException {
        Firestation firestation = FirestationMapper.toEntity(firestationDto);
        logger.info("Attempting to add firestation: {}",firestation);
        if (firestation != null) {
            firestations.add(firestation);
            dataService.writeData();
            logger.info("firestation successfully added.");
            return FirestationMapper.toDto(firestation);
        } else {
            logger.warn("Invalid firestation data received.");
            throw new SafetyNetBadRequestException("firestation should be full");
        }
    }

    /**
     * Update an existing firestation's details.
     * Matches by address
     * @param firestationDto the firestation data with updated fields
     * @return the updated firestation
     * @throws IOException if the data cannot be saved
     */
    public FirestationDto editFirestation(FirestationDto firestationDto) throws IOException {
        Firestation firestationUpdated = null;
        try {
            Firestation firestation = FirestationMapper.toEntity(firestationDto);
            if (StringUtils.hasText(firestation.getAddress()) && firestation.getStation() != null) {
                for (Firestation firestationDB : firestations) {
                    if (firestation.equals(firestationDB)) {
                        // Update each field if the new value is present
                        firestationDB.setStation(firestation.getStation());
                        logger.info("Station updated");
                        firestationUpdated = firestationDB;
                        break;
                    }
                }
            }
            if (firestationUpdated != null) {
                dataService.writeData();
                logger.info("change saved successfully");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.warn("Invalid input while editing firestation: {}", e.getMessage());
            throw new SafetyNetBadRequestException("firestation should be full");
        }
        return FirestationMapper.toDto(firestationUpdated);
    }

    /**
     * Delete a firestation identified by address and station number.
     * @param adressX the address of the firestation
     * @param stationY the number station of the firestation
     * @return true if the firestation was found and deleted, false otherwise
     * @throws IOException if the data can't be deleted
     */
    public Boolean deleteFirestation(String adressX, Integer stationY) throws IOException {
        boolean found = false;
        if (StringUtils.hasText(adressX) && stationY != null) {
            logger.info("search for person to delete.");
            for (Firestation firestationDB : firestations) {
                if (adressX.equals(firestationDB.getAddress()) && stationY.equals(firestationDB.getStation())) {
                    firestations.remove(firestationDB);
                    dataService.writeData();
                    logger.info("firestation successfully deleted");
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
