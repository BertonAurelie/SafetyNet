package com.aboc.safetyNet.exception;

//utilisé pour addPerson, champ pas entièrement rempli/requete vide
public class SafetyNetBadRequestException extends RuntimeException {
    public SafetyNetBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
