package com.CustomExceptions;

/**
 * Exception used specifically if an invalid isolation exception level was provided
 */
public class InvalidIsolationLevelException extends RuntimeException {
    public InvalidIsolationLevelException(String message) {
        super(message);
    }
}
