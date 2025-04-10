package com.aboc.safetyNet.service;

import com.aboc.safetyNet.exception.SafetyNetBadRequestException;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.dto.request.MedicalRecordDto;
import com.aboc.safetyNet.model.mapper.MedicalRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    private List<MedicalRecord> medicalRecords;
    private DataService dataService;

    public MedicalRecordService(DataService dataService) throws IOException {
        this.dataService = dataService;
        medicalRecords = dataService.loadFileData().getMedicalrecords();
    }

    /**
     * Retrieve the full list of medicalrecord from data file
     * @return a list of {@Link medicalRecord}
     */
    public List<MedicalRecord> getAllMedicalRecord() {
        logger.info("Loading list of all MedicalRecord.");
        return medicalRecords;
    }

    /**
     * Add new medicalRecord to the list and save it to the data file
     * @param medicalRecordDto
     * @return the medicalRecord added
     * @throws IOException if the data can't be saved
     * @throws SafetyNetBadRequestException if the medicalRecord is incomplete
     */
    public MedicalRecordDto addNewMedicalRecord(MedicalRecordDto medicalRecordDto) throws IOException {
        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordDto);
        logger.info("Attempting to add firestation: {}",medicalRecord);
        if (medicalRecord != null) {
            medicalRecords.add(medicalRecord);
            dataService.writeData();
            logger.info("medicalRecord successfully added.");
            return MedicalRecordMapper.toDto(medicalRecord);
        } else {
            logger.warn("Invalid medicalRecord data received.");
            throw new SafetyNetBadRequestException("medicalRecord should be full");
        }
    }

    /**
     * Update an existing medicalRecord's details.
     * matches by first name and last name.
     * @param medicalRecordDto the medicalRecord data with updated fields
     * @return the updated medicalRecord
     * @throws IOException if the data can't be saved
     */
    public MedicalRecordDto updatedMedicalRecord(MedicalRecordDto medicalRecordDto) throws IOException {
        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordDto);
        MedicalRecord medicalRecordUpdated = null;
        if (StringUtils.hasText(medicalRecord.getFirstName()) && StringUtils.hasText(medicalRecord.getLastName())) {
            for (MedicalRecord medicalRecordDb : medicalRecords) {
                if (medicalRecord.equals(medicalRecordDb)) {
                    // Update each field if the new value is present
                    if (StringUtils.hasText(medicalRecord.getBirthdate())) {
                        medicalRecordDb.setBirthdate(medicalRecord.getBirthdate());
                        logger.info("Birthdate updated");
                    }
                    if (medicalRecord.getMedications() != null) {
                        medicalRecordDb.setMedications(medicalRecord.getMedications());
                        logger.info("Medications updated");
                    }
                    if (medicalRecord.getAllergies() != null) {
                        medicalRecordDb.setAllergies(medicalRecord.getAllergies());
                        logger.info("Allergies updated");
                    }
                    medicalRecordUpdated = medicalRecordDb;
                    break;
                }
            }
        }
        if (medicalRecordUpdated != null) {
            dataService.writeData();
            logger.info("change saved successfully");
        }
        return MedicalRecordMapper.toDto(medicalRecord);
    }

    /**
     * Delete a medicalrecord identified by first name and last name.
     * @param firstNameX the first name of the medicalRecord
     * @param lastNameX the last name of the medicalRecord
     * @return true if the medicalRecord was found and deleted, false otherwise
     * @throws  IOException if the data can't be deleted
     */
    public Boolean deleteMedicalRecord(String firstNameX, String lastNameX) {
        boolean found = false;
        if (StringUtils.hasText(firstNameX) && StringUtils.hasText(lastNameX)) {
            for (MedicalRecord medicalRecordDb : medicalRecords) {
                if (firstNameX.equals(medicalRecordDb.getFirstName()) && lastNameX.equals(medicalRecordDb.getLastName())) {
                    medicalRecords.remove(medicalRecordDb);
                    dataService.writeData();
                    logger.info("MedicalRecord successfully deleted");
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

}
