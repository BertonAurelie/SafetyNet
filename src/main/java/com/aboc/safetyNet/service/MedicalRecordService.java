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

    public List<MedicalRecord> getAllMedicalRecord() {
        System.out.println(medicalRecords);
        return medicalRecords;
    }

    public MedicalRecordDto addNewMedicalRecord(MedicalRecordDto medicalRecordDto) throws IOException {
        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordDto);
        logger.info(medicalRecord.toString());
        if (medicalRecord != null) {
            medicalRecords.add(medicalRecord);
            dataService.writeData();
            logger.info("medicalRecord registered in the database");
            return MedicalRecordMapper.toDto(medicalRecord);
        } else {
            logger.info(medicalRecord.toString());
            logger.info("disabled medicalRecord");
            throw new SafetyNetBadRequestException("medicalRecord should be full");
        }
    }

    public MedicalRecordDto updatedMedicalRecord(MedicalRecordDto medicalRecordDto) throws IOException {
        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordDto);
        MedicalRecord medicalRecordUpdated = null;
        if (StringUtils.hasText(medicalRecord.getFirstName()) && StringUtils.hasText(medicalRecord.getLastName())) {
            for (MedicalRecord medicalRecordDb : medicalRecords) {
                if (medicalRecord.equals(medicalRecordDb)) {
                    if (StringUtils.hasText(medicalRecord.getBirthdate())) {
                        medicalRecordDb.setBirthdate(medicalRecord.getBirthdate());
                        logger.info("Birthdate has modified");
                    }
                    if (medicalRecord.getMedications() != null) {
                        medicalRecordDb.setMedications(medicalRecord.getMedications());
                        logger.info("Medications has modified");
                    }
                    if (medicalRecord.getAllergies() != null) {
                        medicalRecordDb.setAllergies(medicalRecord.getAllergies());
                        logger.info("Allergies has modified");
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
