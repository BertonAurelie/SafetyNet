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

    /**
     * Retrieve persons covered by a fire station number.
     * includes the count of adults and children(age <= 18).
     *
     * @param stationNumber the fire station number
     * @return FirestationCoverageResponse with list of persons & count of adults/children
     */
    public FirestationCoverageResponse foundPersonWithStationNumberOfFirestation(Integer stationNumber) {
        List<PersonCoveredByStationResponse> personOfStationDto = new ArrayList<>();
        int adultCount = 0;
        int childrenCount = 0;

        // Get all addresses linked to this firestation
        var firestationAddresses = firestations.stream()
                .filter(s -> s.getStation().equals(stationNumber))
                .map(s -> s.getAddress())
                .toList();

        // Get all persons with the same address
        var coveredPersons = persons.stream()
                .filter(p -> firestationAddresses.contains(p.getAddress()))
                .toList();

        if (stationNumber != null) {
            logger.info("searching for people covered by fire station {}", stationNumber);
            for (Person person : coveredPersons) {
                personOfStationDto.add(PersonResponseMapper.toDto(person));

                for (MedicalRecord medicalRecord : medicalRecords) {
                    if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                        if (getElapsedYears(medicalRecord.getBirthdate()) <= 18) {
                            childrenCount++;
                        } else {
                            adultCount++;
                        }

                        break;
                    }
                }
            }
        }

        FirestationCoverageResponse firestationCoverageResponse = new FirestationCoverageResponse(personOfStationDto, adultCount, childrenCount);
        return firestationCoverageResponse;
    }

    /**
     * retrieves children living at the param address and list of other  family members.
     * @param address
     * @return ChildAlertResponse with children and their family.
     */
    public ChildAlertResponse getChildrenAtAddress(String address) {
        List<ChildWithFamilyResponse> childrenDto = new ArrayList<>();

        if (StringUtils.hasText(address)) {
            for (Person person : persons) {
                if (person.getAddress().equals(address)) {

                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (person.getFirstName().equals(medicalRecord.getFirstName())) {
                            long age = getElapsedYears(medicalRecord.getBirthdate());

                            if (age <= 18) {
                                List<FamilyMemberResponse> familyMember = getFamilyMembers(person);
                                ChildWithFamilyResponse persondto = TargetChildMapper.toDto(person, age, familyMember);
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

    /**
     * Retrieves phone numbers of persons covered by a specific fire station.
     *
     * @param stationNumber the fire station number
     * @return PhoneAlertResponse with a list of phone numbers
     */
    public PhoneAlertResponse phoneAlert(Integer stationNumber) {
        List<String> phonesList = new ArrayList<>();

        if (stationNumber != null) {
            logger.info("Fetching phone numbers for station {}", stationNumber);
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

    /**
     * Retrieves information about persons living at a specific address
     * and the station number serving it.
     *
     * @param address the home address
     * @return FireAddressResponse with resident details and station number
     */
    public FireAddressResponse fire(String address) {
        List<FirePersonInfoResponse> firePersonInfoResponses = new ArrayList<>();
        Integer station = null;

        if (address != null) {
            logger.info("Fetching persons and station for address: {}", address);
            for (Firestation firestation : firestations) {
                if (address.equals(firestation.getAddress())) {
                    firePersonInfoResponses = getPersonInfo(firestation);
                    station = firestation.getStation();
                }
            }
        }

        FireAddressResponse fireAddressResponse = new FireAddressResponse(station, firePersonInfoResponses);
        return fireAddressResponse;
    }

    /**
     * Retrieves grouped resident data for all households covered by multiple fire stations.
     *
     * @param stations list of fire station numbers
     * @return FloodStationsResponse with grouped household data
     */
    public FloodStationsResponse flood(List<Integer> stations) {
        List<FloodHouseResponse> floodHouse = new ArrayList<>();
        FloodStationsResponse floodStationsResponse = new FloodStationsResponse();

        logger.info("Generating flood list for stations: {}", stations);
        for (int i = 0; i < stations.size(); i++) {
            String address = null;
            for (Firestation firestation : firestations) {

                if (firestation.getStation().equals(stations.get(i))) {
                    address = firestation.getAddress();
                    List<FirePersonInfoResponse> PersonsAtThisAddress = getPersonInfo(firestation);
                    FloodHouseResponse floodHouseResponse = new FloodHouseResponse(address, PersonsAtThisAddress);
                    floodHouse.add(floodHouseResponse);
                }
            }
        }
        floodStationsResponse.setFloodHouse(floodHouse);
        return floodStationsResponse;
    }

    /**
     * Retrieves personal and medical info of all persons sharing the same last name.
     *
     * @param lastName the last name to search for
     * @return list of PersonInfoResponse with detailed personal info
     */
    public List<PersonInfoResponse> getPersonsByLastName(String lastName) {
        List<PersonInfoResponse> personInfoList = new ArrayList<>();

        if (lastName != null) {
            for (Person person : persons) {
                if (person.getLastName().equals(lastName)) {
                    PersonInfoResponse personDto = PersonInfoMapper.toDto(person);

                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                            long agePerson = getElapsedYears(medicalRecord.getBirthdate());
                            personDto.setAge(agePerson);
                            personDto.setMedications(medicalRecord.getMedications());
                            personDto.setAllergies(medicalRecord.getAllergies());

                            personInfoList.add(personDto);
                            break;
                        }
                    }
                }
            }
        }
        return personInfoList;
    }

    /**
     * Retrieves emails of all persons living in the specified city.
     *
     * @param city the city name
     * @return list of email addresses
     */
    public List<String> getEmailsByCity(String city) {
        List<String> personInfoList = new ArrayList<>();

        if (city != null) {
            for (Person person : persons) {
                if (person.getCity().equals(city)) {
                    personInfoList.add(person.getEmail());
                }
            }
        }

        return personInfoList;
    }

    /**
     * Helper method to retrieve full info (including medical data) of residents at a firestation address.
     */
    private List<FirePersonInfoResponse> getPersonInfo(Firestation firestation) {
        List<FirePersonInfoResponse> PersonsAtThisAddress = new ArrayList<>();
        for (Person person : persons) {
            if (person.getAddress().equals(firestation.getAddress())) {
                FirePersonInfoResponse personDto = FirePersonInfoMapper.toDto(person);
                PersonsAtThisAddress.add(personDto);

                for (MedicalRecord medicalRecord : medicalRecords) {
                    if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                        long agePerson = getElapsedYears(medicalRecord.getBirthdate());
                        personDto.setAge(agePerson);
                        personDto.setMedicalRecord(medicalRecord.getMedications());
                        personDto.setAllergies(medicalRecord.getAllergies());
                        break;
                    }
                }
            }
        }
        return PersonsAtThisAddress;
    }

    /**
     * Helper method to find household members excluding the given person.
     */
    private List<FamilyMemberResponse> getFamilyMembers(Person person) {
        List<FamilyMemberResponse> familyMember = new ArrayList<>();

        for (Person family : persons) {
            if (family.getAddress().equals(person.getAddress()) && family != person) {
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

    /**
     * Converts a string date to LocalDate using the predefined formatter.
     */
    private LocalDate convertToLocalDate(String strDate) {
        LocalDate date = LocalDate.parse(strDate, formatter);
        return date;
    }

    /**
     * Calculates the number of years elapsed since the given date string.
     */
    private long getElapsedYears(String strDate) {
        LocalDate date1 = convertToLocalDate(strDate);
        LocalDate date2 = LocalDate.now();
        long elapsedYears = ChronoUnit.YEARS.between(date1, date2);
        return elapsedYears;
    }
}


