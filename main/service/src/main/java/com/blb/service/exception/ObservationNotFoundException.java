package com.blb.service.exception;

public class ObservationNotFoundException extends Exception {
    public ObservationNotFoundException(String message) {
        super(message);
    }

    public ObservationNotFoundException(Long id) {
        super("Observation with id: " + id + " not found");
    }
}
