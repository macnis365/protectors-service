package com.protectors.app.protectorsservice.customexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        LOGGER.error("Exception " + ex.getStackTrace());
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissionNotFound.class)
    public final ResponseEntity<Object> handleMissionNotFoundException(MissionNotFound ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        LOGGER.error("Exception " + ex);
        return new ResponseEntity(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuperheroNotFound.class)
    public final ResponseEntity<Object> handleSuperheroNotFoundException(SuperheroNotFound ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        LOGGER.error("Exception " + ex);
        return new ResponseEntity(details, HttpStatus.NOT_FOUND);
    }
}
