package com.CustomExceptions;

public class CsvReadException extends Exception {
    public CsvReadException(String message, Throwable error) {
        super(message, error);
    }
}
