package com.aboc.safetyNet.controller;

import com.aboc.safetyNet.model.dto.response.*;
import com.aboc.safetyNet.service.SafetyNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SafetyNetController {
    private static final Logger logger = LoggerFactory.getLogger(SafetyNetController.class);
    private final SafetyNetService safetyNetService;

    public SafetyNetController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
        logger.info("loading SafetyNetController");
    }

    /**
     * Retrieves a list of children living at a given address, along with household members.
     *
     * @param address to search
     * @return ChildAlertResponse containing children and adults living at the address
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getchildAlert(@RequestParam String address) {
        return new ResponseEntity<>(safetyNetService.getChildrenAtAddress(address), HttpStatus.OK);
    }

    /**
     * Retrieves the phone numbers of residents covered by a specific firestation.
     *
     * @param station the firestation number
     * @return PhoneAlertResponse containing phone numbers of covered persons
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertResponse> getPhoneAlert(@RequestParam Integer station) {
        return new ResponseEntity<>(safetyNetService.phoneAlert(station), HttpStatus.OK);
    }

    /**
     * Retrieves information about all people living at a given address,
     * including the firestation number serving that address.
     *
     * @param address to search
     * @return FireAddressResponse with details about residents and station number
     */
    @GetMapping("/fire")
    public ResponseEntity<FireAddressResponse> fireGetAddressPeople(@RequestParam String address) {
        return new ResponseEntity<>(safetyNetService.fire(address), HttpStatus.OK);
    }

    /**
     * Retrieves households by firestation numbers in case of a flood.
     *
     * @param stations list of firestation numbers
     * @return FloodStationsResponse with a list of households covered by the stations
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<FloodStationsResponse> floodGetAddressPeople(@RequestParam String stations) {
        List<Integer> myList = new ArrayList<>();
        for (String s : stations.split(",")) {
            myList.add(Integer.valueOf(s));
        }
        return new ResponseEntity<>(safetyNetService.flood(myList), HttpStatus.OK);
    }

    /**
     * Retrieves detailed person information based on last name.
     *
     * @param lastName to search
     * @return a list of PersonInfoResponse objects containing person details
     */
    @GetMapping("/personInfoLastName")
    public ResponseEntity<List<PersonInfoResponse>> getPersonInfo(@RequestParam String lastName) {
        return new ResponseEntity<>(safetyNetService.getPersonsByLastName(lastName), HttpStatus.OK);
    }

    /**
     * Retrieves all email addresses of residents living in a given city.
     *
     * @param city to search
     * @return a list of email addresses
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailByCity(@RequestParam String city) {
        return new ResponseEntity<>(safetyNetService.getEmailsByCity(city), HttpStatus.OK);
    }
}
