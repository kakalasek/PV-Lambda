package com.CustomExceptions;

/**
 * Exception crafted for specifically the menu class. It is thrown when the programmer tries to enter
 * a message for a menu option, which is too long to display in its respective menu.
 */
public class RowCharactersExceededException extends RuntimeException{
    public RowCharactersExceededException(String message) {
        super(message);
    }
}
