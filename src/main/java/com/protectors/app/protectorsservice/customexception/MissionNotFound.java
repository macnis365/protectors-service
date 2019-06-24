package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissionNotFound extends RuntimeException{
    public MissionNotFound(Long id) {
        super("Mission does not exist: " + id);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this.getCause();
    }
}
