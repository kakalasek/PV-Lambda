package com.utils.ScannerWrapper;

import java.util.Scanner;

/**
 * This class provides one instance of the scanner object to the whole application.
 * The Scanner uses the System.in stream
 */
public class ScannerWrapper {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Simply returns the instance of the static Scanner object, which uses the System.in stream
     * @return Instance of Scanner object using the System.in stream
     */
    public static Scanner getScanner(){
        return scanner;
    }

    /**
     * Closes the static Scanner object. Note that once you close this object, you won't be able to use
     * the Scanner System.in stream in your program anymore
     */
    public static void closeScanner(){
        scanner.close();
    }
}
