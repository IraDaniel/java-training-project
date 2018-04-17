package com.company.controller;

import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice("com.company")
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public void defaultErrorHandler(HttpServletResponse response, Exception e) {
        try {
            throw e;
        } catch (DogNotFoundException dogNotFound) {
            handleAnnotatedResponseStatus(response, dogNotFound);
            LOGGER.error(dogNotFound.getMessage(), dogNotFound);
        } catch (DogException dogException) {
            updateResponse(response, dogException.getStatus().value());
            LOGGER.error(dogException, dogException);
        } catch (Exception other) {

        }
    }

    private void updateResponse(HttpServletResponse response, final int status) {
        try {
            response.sendError(status);
        } catch (IOException e1) {

        }
        response.setStatus(status);
    }

    private void handleAnnotatedResponseStatus(HttpServletResponse response, Exception e) {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class).code();
            updateResponse(response, httpStatus.value());
        }
    }
}
