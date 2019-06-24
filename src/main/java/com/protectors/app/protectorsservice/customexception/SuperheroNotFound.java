package com.protectors.app.protectorsservice.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperheroNotFound extends Exception {
    public SuperheroNotFound(){
        super("Superhero does not exist");
    }
}
