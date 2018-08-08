package com.company.exception;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DogNotFoundException extends DogException {

    private static final String MSG_TEMPLATE = "Dog with id %s does not exist";

    public DogNotFoundException(UUID dogUuid) {
        super(String.format(MSG_TEMPLATE, dogUuid));
        status = HttpStatus.NOT_FOUND;
    }
}
