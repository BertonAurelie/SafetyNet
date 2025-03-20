package com.aboc.safetyNet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//utilisé pour addPerson, champ pas entièrement rempli/requete vide
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Entity not filled")
public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
