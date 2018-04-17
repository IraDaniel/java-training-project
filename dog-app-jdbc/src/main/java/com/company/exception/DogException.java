package com.company.exception;


import org.springframework.http.HttpStatus;

public class DogException extends RuntimeException {

    protected HttpStatus status;
    public DogException(String message) {
        super(message);
    }


    public DogException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
