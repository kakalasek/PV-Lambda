package com.utils.InputChecker;

import java.sql.Date;

public class InputChecker {

    public static boolean isPositiveNumber(String input){
        String positiveNumberRegex = "\\d+";
        return input.matches(positiveNumberRegex);
    }

}
