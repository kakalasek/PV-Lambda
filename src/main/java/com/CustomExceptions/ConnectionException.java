package com.CustomExceptions;

/**
 * A generic exception used when there is any problem with the database connection
 */
public class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable error) {
        super(message, error);
    }
}
