package com.utils.HandyTools;

import com.CustomExceptions.InvalidOptionException;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class HandyTools {

    public static <T> int chooseFromList(String message, ArrayList<T> list) throws InvalidOptionException {
        Scanner sc = ScannerWrapper.getScanner();
        System.out.println(message);
        String pickString = sc.nextLine();
        if(!InputChecker.isPositiveNumber(pickString)){
            throw new InvalidOptionException("Your pick must be a number within the respective range");
        }

        int pick = Integer.parseInt(pickString);
        if(pick > list.size()){
            throw new InvalidOptionException("Your pick must be a number within the respective range");
        }

        return pick;
    }

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
}
