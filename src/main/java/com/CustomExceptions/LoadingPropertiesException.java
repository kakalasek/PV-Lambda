package com.CustomExceptions;

/**
 * Exception used to signify, that reading the configuration file ended with an error
 */
public class LoadingPropertiesException extends RuntimeException {
    public LoadingPropertiesException(String message, Throwable error) {
        super(message, error);
    }
}
