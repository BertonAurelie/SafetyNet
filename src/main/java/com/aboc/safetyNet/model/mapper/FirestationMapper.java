package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.Firestation;
import com.aboc.safetyNet.model.dto.FirestationDto;

public class FirestationMapper {

    public FirestationMapper() {}

    //Convertit un DTO en entité Person (utilisé lors de l'ajout)
    public static Firestation toEntity(FirestationDto firestationDTO) {
        Firestation firestation = new Firestation();

        firestation.setAddress(firestationDTO.getAddress());
        firestation.setStation(firestationDTO.getStation());

        return firestation;
    }

    //Convertit une entité person en personDTO (utilisé lors de la réponse API)
    public static FirestationDto toDto(Firestation firestation) {
        FirestationDto firestationDTO = null;

        if(firestation != null){
            firestationDTO = new FirestationDto();

            firestationDTO.setStation(firestation.getStation());
            firestationDTO.setAddress(firestation.getAddress());
        }

        return firestationDTO;
    }
}
