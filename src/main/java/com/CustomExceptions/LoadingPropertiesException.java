package com.CustomExceptions;

public class LoadingPropertiesException extends RuntimeException {
    public LoadingPropertiesException(String message) {
        super(message);
    }
}
