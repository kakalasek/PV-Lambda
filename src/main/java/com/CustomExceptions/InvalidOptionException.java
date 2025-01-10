package com.CustomExceptions;

/**
 * Exception used whenever the user enters something they are not supposed to
 */
public class InvalidOptionException extends Exception {
    public InvalidOptionException(String message) {
        super(message);
    }
}
