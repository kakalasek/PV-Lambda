package com.utils.InputChecker;

import com.utils.FileUtils.FileUtils;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.sql.Date;

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

    /**
     * Validates if the filepath is a valid absolute filepath
     * @param absoluteFilepath The filepath you want to validate
     * @return True if the filepath is valid, False if not
     */
    public static boolean isValidAbsoluteFilepath(String absoluteFilepath) {
        try {
            Paths.get(absoluteFilepath);

            File f = new File(absoluteFilepath);

            return f.exists() && !f.isDirectory();

        } catch (InvalidPathException e){
            return false;
        }
    }

}
