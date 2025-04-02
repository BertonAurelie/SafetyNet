package com.aboc.safetyNet.service;

import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.*;
import com.aboc.safetyNet.model.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SafetyNetService {
    private DataService dataService;
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;
    private final static Logger logger = LoggerFactory.getLogger(SafetyNetService.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withLocale(Locale.FRANCE);

    public SafetyNetService(DataService dataService) throws IOException {
        this.dataService = dataService;
        persons = dataService.loadFileData().getPersons();
        firestations = dataService.loadFileData().getFirestations();
        medicalRecords = dataService.loadFileData().getMedicalrecords();
    }

    public FirestationCoverageResponse foundPersonWithStationNumberOfFirestation(Integer stationNumber) {
        List<Person> personOfStation = new ArrayList<>();
        List<PersonCoveredByStationResponse> personOfStationDto = new ArrayList<>();
        int adultCount = 0;
        int childrenCount = 0;

        if (stationNumber != null) {
            for (Firestation firestation : firestations) {
                if (stationNumber.equals(firestation.getStation())) {
                    for (Person person : persons) {
                        if (person.getAddress().equals(firestation.getAddress())) {
                            personOfStation.add(person);

                            for (MedicalRecord medicalRecord : medicalRecords) {
                                if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                                    if (getElapsedYears(medicalRecord.getBirthdate()) <= 18) {
                                        childrenCount++;
                                    } else {
                                        adultCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Person person : personOfStation) {
            PersonCoveredByStationResponse persondto = PersonResponseMapper.toDto(person);
            personOfStationDto.add(persondto);
        }
        FirestationCoverageResponse firestationCoverageResponse = new FirestationCoverageResponse(personOfStationDto, adultCount, childrenCount);
        return firestationCoverageResponse;
    }

    public ChildAlertResponse getChildrenAtAddress(String address) {
        List<ChildWithFamilyResponse> childrenDto = new ArrayList<>();

        if (StringUtils.hasText(address)) {
            for (Person person : persons) {
                if (person.getAddress().equals(address)) {

                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (person.getFirstName().equals(medicalRecord.getFirstName())) {
                            long ageTargetChild = getElapsedYears(medicalRecord.getBirthdate());

                            if (ageTargetChild <= 18) {
                                List<FamilyMemberResponse> familyMember = getFamilyMembers(person);
                                ChildWithFamilyResponse persondto = TargetChildMapper.toDto(person, ageTargetChild, familyMember);
                                childrenDto.add(persondto);
                            }
                        }
                    }
                }
            }
        }

        ChildAlertResponse childAlertResponse = new ChildAlertResponse(childrenDto);
        return childAlertResponse;
    }

    public PhoneAlertResponse phoneAlert(Integer stationNumber) {
        List<String> phonesList = new ArrayList<>();
        if (stationNumber != null) {
            for (Firestation firestation : firestations) {
                if (stationNumber.equals(firestation.getStation())) {
                    for (Person person : persons) {
                        if (person.getAddress().equals(firestation.getAddress())) {
                            phonesList.add(person.getPhone());
                        }
                    }
                }
            }
        }
        PhoneAlertResponse phoneAlertResponse = PhoneAlertMapper.toDto(phonesList);
        return phoneAlertResponse;
    }

    public FireAddressResponse fire(String address) {
        List<FirePersonInfoResponse> firePersonInfoResponses = new ArrayList<>();
        Integer station = null;

        if (address != null) {
            for (Firestation firestation : firestations) {
                if (address.equals(firestation.getAddress())) {
                    station = firestation.getStation();
                    for (Person person : persons) {
                        if (person.getAddress().equals(firestation.getAddress())) {
                            FirePersonInfoResponse firePersonInfoResponse = FirePersonInfoMapper.toDto(person);
                            firePersonInfoResponses.add(firePersonInfoResponse);

                            for (MedicalRecord medicalRecord : medicalRecords) {
                                if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                                    long agePerson = getElapsedYears(medicalRecord.getBirthdate());
                                    firePersonInfoResponse.setAge(agePerson);
                                    firePersonInfoResponse.setMedicalRecord(medicalRecord.getMedications());
                                    firePersonInfoResponse.setAllergies(medicalRecord.getAllergies());
                                }
                            }
                        }
                    }
                }
            }
        }
        FireAddressResponse fireAddressResponse = new FireAddressResponse(station, firePersonInfoResponses);

        return fireAddressResponse;
    }

    private List<FamilyMemberResponse> getFamilyMembers(Person person) {
        List<FamilyMemberResponse> familyMember = new ArrayList<>();

        for (Person family : persons) {
            if (family.getAddress().equals(person.getAddress()) && !family.getFirstName().equals(person.getFirstName()) && !family.getLastName().equals(person.getLastName())) {
                FamilyMemberResponse familyMemberResponse = ChildAlertMapper.toDto(family);

                familyMember.add(familyMemberResponse);
                for (MedicalRecord medicalRecordFamily : medicalRecords) {
                    if (family.getFirstName().equals(medicalRecordFamily.getFirstName())) {
                        long ageFamilyMember = getElapsedYears(medicalRecordFamily.getBirthdate());
                        familyMemberResponse.setAge(ageFamilyMember);
                    }
                }
            }
        }
        return familyMember;
    }


    private LocalDate convertToLocalDate(String strDate) {
        LocalDate date = LocalDate.parse(strDate, formatter);
        return date;
    }

    private long getElapsedYears(String strDate) {
        LocalDate date1 = convertToLocalDate(strDate);
        LocalDate date2 = LocalDate.now();
        long elapsedYears = ChronoUnit.YEARS.between(date1, date2);
        return elapsedYears;
    }
}

//localhost:8080/flood/stations?stations=<a list of
//station_numbers>
//Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette
//liste doit regrouper les personnes par adresse. Elle doit aussi inclure le nom, le
//numéro de téléphone et l'âge des habitants, et faire figurer leurs antécédents
//médicaux (médicaments, posologie et allergies) à côté de chaque nom.


//localhost:8080/personInfolastName=<lastName>
//Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents
//médicaux (médicaments, posologie et allergies) de chaque habitant. Si plusieurs
//personnes portent le même nom, elles doivent toutes apparaître.


//localhost:8080/communityEmail?city=<city>
//Cette url doit retourner les adresses mail de tous les habitants de la ville.



