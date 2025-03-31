package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.dto.FirestationDto;
import com.aboc.safetyNet.model.dto.MedicalRecordDto;
import com.aboc.safetyNet.model.dto.PersonUpdatedDto;
import com.aboc.safetyNet.service.FirestationService;
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

    public MedicalRecordController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
        logger.info("loading FirestationController");
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecord(){
        logger.info("loading getAllMedicalRecord");
        return new ResponseEntity<>(medicalRecordService.getAllMedicalRecord(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDto> addMedicalRecord(@RequestBody @Valid MedicalRecordDto medicalRecordDto) throws IOException {
        return new ResponseEntity<>(medicalRecordService.addNewMedicalRecord(medicalRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MedicalRecordDto> UpdateMedicalRecord(@RequestBody MedicalRecordDto medicalRecordDto) throws IOException {
        return new ResponseEntity<>(medicalRecordService.updatedMedicalRecord(medicalRecordDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        Boolean delete = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (delete) {
            return new ResponseEntity<>("MedicalRecord successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("MedicalRecord to delete not found", HttpStatus.NOT_FOUND);
    }
}
