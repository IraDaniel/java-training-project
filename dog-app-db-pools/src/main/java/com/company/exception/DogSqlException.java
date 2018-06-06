package com.company.exception;


public class DogSqlException extends DogException{

    public DogSqlException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
