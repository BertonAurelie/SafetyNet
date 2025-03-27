package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.dto.FirestationDto;
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

    public List<Firestation> getAllFirestation() {
        System.out.println(firestations);
        return firestations;
    }

    public FirestationDto addNewFirestation(FirestationDto firestationDto) throws IOException {
        Firestation firestation = FirestationMapper.toEntity(firestationDto);
        logger.info(firestation.toString());
        if (firestation != null) {
            firestations.add(firestation);
            dataService.writeData();
            logger.info("firestation registered in the database");
            return FirestationMapper.toDto(firestation);
        } else {
            logger.info(firestation.toString());
            logger.info("disabled firestation");
            throw new SafetyNetBadRequestException("firestation should be full");
        }
    }

    //TODO :: Ne fonctionne pas.
    public FirestationDto editFirestation(FirestationDto firestationDto) throws IOException {
        Firestation firestation = FirestationMapper.toEntity(firestationDto);
        Firestation firestationUpdated = null;
        if (StringUtils.hasText(firestation.getAddress()) && firestation.getStation() != null) {
            for (Firestation firestationDB : firestations) {
                if (firestation.equals(firestationDB)) {
                    firestationDB.setStation(firestation.getStation());
                    logger.info("Station modified");
                    firestationUpdated = firestationDB;
                    break;
                }
            }
        }
        if (firestationUpdated != null) {
            dataService.writeData();
            logger.info("change saved successfully");
        }
        return FirestationMapper.toDto(firestationUpdated);
    }

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
