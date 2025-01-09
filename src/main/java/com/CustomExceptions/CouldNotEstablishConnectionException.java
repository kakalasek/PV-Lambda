package com.CustomExceptions;

public class CouldNotEstablishConnectionException extends RuntimeException {
    public CouldNotEstablishConnectionException(String message) {
        super(message);
    }
}
