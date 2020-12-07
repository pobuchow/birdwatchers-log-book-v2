package com.blb.service.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super(MessageFormat.format("User with the name: {0} not found", username));
    }
}
