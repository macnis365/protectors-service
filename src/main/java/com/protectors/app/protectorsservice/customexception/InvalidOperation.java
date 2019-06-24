package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidOperation extends Exception {
    public InvalidOperation() {
        super("Invalid operation");
    }
}
