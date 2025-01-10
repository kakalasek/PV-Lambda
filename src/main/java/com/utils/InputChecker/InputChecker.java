package com.utils.InputChecker;

/**
 * Provides methods for simple input checking, return true or false
 */
public class InputChecker {

    /**
     * As the name suggests, this method simply verifies, if the string input is a positive number.
     * Note, that it only works on natural numbers (int, not double)
     * @param input String, which you want to validate
     * @return True, if the number is truly a positive natural number, False, if it is not
     */
    public static boolean isPositiveNumber(String input){
        String positiveNumberRegex = "\\d+";
        return input.matches(positiveNumberRegex);
    }

}
