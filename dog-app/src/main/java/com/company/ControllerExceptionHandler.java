package com.company;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public void defaultErrorHandler(HttpServletResponse response, Exception e) {
        try {

        } catch (DogNotFoundException dogNotFound) {

        }
    }
}
