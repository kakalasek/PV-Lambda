package com.CustomExceptions;

public class CouldNotEstablishConnectionException extends Exception {
    public CouldNotEstablishConnectionException(String message) {
        super(message);
    }
}
