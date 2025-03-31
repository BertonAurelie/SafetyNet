package com.aboc.safetyNet.service;

import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.MedicalRecord;
import com.aboc.safetyNet.model.Person;
import com.aboc.safetyNet.model.dto.response.FirestationCoverageResponse;
import com.aboc.safetyNet.model.dto.response.PersonCoveredByStationResponse;
import com.aboc.safetyNet.model.mapper.PersonResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    private FirestationService firestationService;
    private PersonService personService;
    private MedicalRecord medicalRecord;

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

//URLs
//localhost:8080/firestation?stationNumber=<station_number>
//Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers
//correspondante. Donc, si le numéro de station = 1, elle doit renvoyer les habitants
//couverts par la station numéro 1. La liste doit inclure les informations spécifiques
//suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit fournir un
//décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
//moins) dans la zone desservie.

//localhost:8080/childAlert?address=<address>
//Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins)
//habitant à cette adresse. La liste doit comprendre le prénom et le nom de famille de
//chaque enfant, son âge et une liste des autres membres du foyer. S'il n'y a pas
//d'enfant, cette url peut renvoyer une chaîne vide.
//localhost:8080/phoneAlert?firestation=<firestation_number>
//Cette url doit retourner une liste des numéros de téléphone des résidents desservis
//par la caserne de pompiers. Nous l'utiliserons pour envoyer des messages texte
//d'urgence à des foyers spécifiques.
//localhost:8080/fire?address=<address>
//Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le
//numéro de la caserne de pompiers la desservant. La liste doit inclure le nom, le
//numéro de téléphone, l'âge et les antécédents médicaux (médicaments, posologie et
//allergies) de chaque personne.
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
