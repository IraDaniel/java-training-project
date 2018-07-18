package com.company.exception;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DogNotFoundException extends DogException {

    public DogNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }
}
