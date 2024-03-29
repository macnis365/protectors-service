package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActiveMissionCannotDelete extends RuntimeException {
    public ActiveMissionCannotDelete(Long id) {
        super("Active mission cannot be deleted");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this.getCause();
    }
}
