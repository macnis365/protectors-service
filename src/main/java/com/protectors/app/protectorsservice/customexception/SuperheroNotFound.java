package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperheroNotFound extends RuntimeException {
    public SuperheroNotFound(Long id) {
        super("Superhero does not exist: " + id);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this.getCause();
    }
}
