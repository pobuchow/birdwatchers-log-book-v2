package com.blb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "User cannot be created")
public class UserCreationException extends Exception {
    public UserCreationException(String message) {
        super(message);
    }
}
