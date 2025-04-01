package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.dto.response.ChildAlertResponse;
import com.aboc.safetyNet.service.SafetyNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SafetyNetController {
    private static final Logger logger = LoggerFactory.getLogger(SafetyNetController.class);
    private final SafetyNetService safetyNetService;

    public SafetyNetController(SafetyNetService safetyNetService){
       this.safetyNetService = safetyNetService;
       logger.info("loading SafetyNetController");
    }


    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getchildAlert(String address){
        return new ResponseEntity<>(safetyNetService.getChildrenAtAddress(address), HttpStatus.OK);
    }
}
