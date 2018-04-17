package com.company.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DogNotFoundException extends DogException {

    public DogNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }
}
