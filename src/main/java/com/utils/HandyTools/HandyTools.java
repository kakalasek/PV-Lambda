package com.utils.HandyTools;

import com.CustomExceptions.InvalidOptionException;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Date;
import java.util.Scanner;

/**
 * Provides useful methods which greatly reduce the number of rows and simplify commonly executed tasks
 */
public class HandyTools {

    /**
     * Lets the user choose an item from the specified list
     * @param message The message to be displayed as the opening prompt before choosing an option
     * @param numberOfItems How many items are in the list. The method checks, if users choice is not bigger
     *                      than this number. 0 is always the first items choice number
     * @return The users pick in an Integer form, if everything went well
     * @throws InvalidOptionException Gets thrown if the user either did not enter a positive natural number
     *                                or the number was bigger than the number of items
     */
    public static int chooseFromList(String message, int numberOfItems) throws InvalidOptionException {
        Scanner sc = ScannerWrapper.getScanner();
        System.out.println(message);
        String pickString = sc.nextLine();
        if(!InputChecker.isPositiveNumber(pickString)){
            throw new InvalidOptionException("Your pick must be a number within the respective range");
        }

        int pick = Integer.parseInt(pickString);
        if(pick > numberOfItems){
            throw new InvalidOptionException("Your pick must be a number within the respective range");
        }

        return pick;
    }

    /**
     * Lets the user enter a date, validate it and return it in a string form.
     * Note that this method always uses the date format YYYY-MM-DD
     * @param dateOf This is the name of the date. It will be used as a part of the prompts. Example: 'expiration date'
     * @return The date in a string form, if everything went well
     * @throws InvalidOptionException Is thrown if the date is in an invalid format, or the user does not enter a date at all
     */
    public static Date pickDate(String dateOf) throws InvalidOptionException {
        try {
            Scanner sc = ScannerWrapper.getScanner();
            System.out.println("Enter date of " + dateOf + " in this format: YYYY-MM-DD");
            String dateString = sc.nextLine();
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e){
            throw new InvalidOptionException("You have entered the " + dateOf + " in an invalid format");

        }
    }


    /**
     * Works very similar to the 'chooseFromList' method, but provides more granular control over the error messages
     * @param prompt The prompt which is displayed before the user chooses a number
     * @param exception1 A message which is displayed, if the user did not enter a positive number
     * @param exception2 A message which is displayed, if the user enter a number that is bigger than the upperLimit
     * @param upperLimit The upper limit of the numbers the user can choose from
     * @return The number in an integer form, if everything goes okay
     * @throws InvalidOptionException Is thrown when the user does not enter a number or the number is outside of range
     */
    public static int choosePositiveNumberWithLimit(String prompt, String exception1, String exception2, int upperLimit) throws InvalidOptionException{
        Scanner sc = ScannerWrapper.getScanner();
        System.out.println(prompt);
        String pickString = sc.nextLine();
        if(!InputChecker.isPositiveNumber(pickString)){
            throw new InvalidOptionException(exception1);
        }

        int pick = Integer.parseInt(pickString);
        if(pick > upperLimit){
            throw new InvalidOptionException(exception2);
        }

        return pick;
    }

    public static String chooseFile(String message) throws InvalidOptionException {
        Scanner sc = ScannerWrapper.getScanner();

        System.out.println(message);
        String absoluteFilepath = sc.nextLine();

        if(!InputChecker.isValidAbsoluteFilepath(absoluteFilepath)) throw new InvalidOptionException("Entered absolute file path is invalid");

        return absoluteFilepath;
    }
}
