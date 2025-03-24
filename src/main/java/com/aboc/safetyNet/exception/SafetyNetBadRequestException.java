package com.aboc.safetyNet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//utilisé pour addPerson, champ pas entièrement rempli/requete vide
public class SafetyNetBadRequestException extends RuntimeException {
    public SafetyNetBadRequestException(String errorMessage) {
        super(errorMessage);
    }


}
