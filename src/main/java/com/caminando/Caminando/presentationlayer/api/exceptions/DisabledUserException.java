package com.caminando.Caminando.presentationlayer.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class DisabledUserException extends RuntimeException {

    public DisabledUserException(String username) {
        super("User " + username + " is disabled.");
    }
}
