package com.blb.service.exception;

import java.text.MessageFormat;

public class ObservationNotFoundException extends Exception {
    public ObservationNotFoundException(String message) {
        super(message);
    }

    public ObservationNotFoundException(Long id) {
        super(MessageFormat.format("Observation with id: {0} not found", id));
    }
}
