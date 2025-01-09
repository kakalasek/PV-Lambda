package com.utils.ScannerWrapper;

import java.util.Scanner;

public class ScannerWrapper {

    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner(){
        return scanner;
    }

    public static void closeScanner(){
        scanner.close();
    }
}
