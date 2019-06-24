package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Input cannot be empty");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this.getCause();
    }
}
