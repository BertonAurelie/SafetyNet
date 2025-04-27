package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.dto.request.MedicalRecordDto;

/**
 * Mapper MedicalRecordDto to MedicalRecord & MedicalRecord to MedicalRecordDto.
 */
public class MedicalRecordMapper {

    public MedicalRecordMapper() {
    }

    //Convertir un DTO en entité Person (utilisé lors de l'ajout)
    public static MedicalRecord toEntity(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setFirstName(medicalRecordDto.getFirstName());
        medicalRecord.setLastName(medicalRecordDto.getLastName());
        medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
        medicalRecord.setMedications(medicalRecordDto.getMedications());
        medicalRecord.setAllergies(medicalRecordDto.getAllergies());

        return medicalRecord;
    }

    //Convertir une entité person en personDTO (utilisé lors de la réponse API)
    public static MedicalRecordDto toDto(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = null;

        if (medicalRecord != null) {
            medicalRecordDto = new MedicalRecordDto();

            medicalRecordDto.setFirstName(medicalRecord.getFirstName());
            medicalRecordDto.setLastName(medicalRecord.getLastName());
            medicalRecordDto.setBirthdate(medicalRecord.getBirthdate());
            medicalRecordDto.setMedications(medicalRecord.getMedications());
            medicalRecordDto.setAllergies(medicalRecord.getAllergies());
        }

        return medicalRecordDto;
    }
}
