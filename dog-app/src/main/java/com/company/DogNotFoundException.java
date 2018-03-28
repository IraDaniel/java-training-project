package com.company;


public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(String message) {
        super(message);
    }
}
