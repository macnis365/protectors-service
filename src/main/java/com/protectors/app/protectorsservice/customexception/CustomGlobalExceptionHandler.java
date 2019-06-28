package com.protectors.app.protectorsservice.customexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleRemainingExceptions(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), request.getDescription(false));
        LOGGER.error("Exception " + exception.getCause());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), request.getDescription(false));
        LOGGER.error("Exception " + exception.getCause());
        return handleExceptionInternal(exception, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MissionNotFound.class)
    public final ResponseEntity<ErrorDetails> handleMissionNotFoundException(MissionNotFound exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), request.getDescription(false));
        LOGGER.error("Exception " + exception.getCause());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuperheroNotFound.class)
    public final ResponseEntity<ErrorDetails> handleSuperheroNotFoundException(SuperheroNotFound exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), request.getDescription(false));
        LOGGER.error("Exception " + exception.getCause());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActiveMissionCannotDelete.class)
    public final ResponseEntity<ErrorDetails> handleCompletedMissionCannotDeleteException(ActiveMissionCannotDelete exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), request.getDescription(false));
        LOGGER.error("Exception " + exception.getCause());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
