package com.chargemate.exception;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
    }

    public StationNotFoundException(Long id) {
        super("Station not found with id: " + id);
    }
} 