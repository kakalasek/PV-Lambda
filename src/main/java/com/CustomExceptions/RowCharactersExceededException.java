package com.CustomExceptions;

public class RowCharactersExceededException extends RuntimeException{
    public RowCharactersExceededException(String message) {
        super(message);
    }
}
