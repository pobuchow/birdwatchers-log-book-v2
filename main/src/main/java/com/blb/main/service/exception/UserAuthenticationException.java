package com.blb.main.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Login failed")
public class UserAuthenticationException extends Exception {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
