package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.dto.request.MedicalRecordDto;
import com.aboc.safetyNet.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
        logger.info("Loading MedicalRecordController");
    }

    /**
     * Retrieves all medical records from the database.
     *
     * @return ResponseEntity containing a list of MedicalRecord
     */
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecord() {
        logger.info("loading getAllMedicalRecord");
        return new ResponseEntity<>(medicalRecordService.getAllMedicalRecord(), HttpStatus.OK);
    }

    /**
     * Adds a new medical record to the database.
     *
     * @param medicalRecordDto containing the new medical record details
     * @return ResponseEntity containing the created MedicalRecordDto
     * @throws IOException if saving the medical record fails
     */
    @PostMapping
    public ResponseEntity<MedicalRecordDto> addMedicalRecord(@RequestBody @Valid MedicalRecordDto medicalRecordDto) throws IOException {
        return new ResponseEntity<>(medicalRecordService.addNewMedicalRecord(medicalRecordDto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing medical record.
     *
     * @param medicalRecordDto containing updated medical record details
     * @return ResponseEntity indicating the update operation with HttpStatus OK
     * @throws IOException if update fails
     */
    @PutMapping
    public ResponseEntity<MedicalRecordDto> UpdateMedicalRecord(@RequestBody MedicalRecordDto medicalRecordDto) throws IOException {
        return new ResponseEntity<>(medicalRecordService.updatedMedicalRecord(medicalRecordDto), HttpStatus.OK);
    }

    /**
     * Deletes a medical record identified by the person's first and last name.
     *
     * @param firstName of person whose medical record is to be deleted
     * @param lastName  of person whose medical record is to be deleted
     * @return ResponseEntity indicating the outcome (success or not found)
     * @throws IOException if deletion fails
     */
    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        Boolean delete = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (delete) {
            return new ResponseEntity<>("MedicalRecord successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("MedicalRecord to delete not found", HttpStatus.NOT_FOUND);
    }
}
