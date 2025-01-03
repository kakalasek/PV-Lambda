package com.MainLoop;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is the main loop of this program. It provides a textual interface to the user and handles input.
 * It also calls all the individual commands.
 */
public class MainLoop {

    private final String helloMessage = """
           
            Hello there, welcome to this simple interface to your garden database. What is it you'd like to do?
            
            """;

    private final String prompt = """
           
            +--------------------------------+
            | Select one of these options    |
            | ============================== |
            | 1) Exit                        |
            +--------------------------------+
            
            """;

    private final Scanner sc;

    public int selectOption(){
        String userSelect = sc.nextLine();

        if (!userSelect.matches("\\d")){
            throw new InputMismatchException("Your pick must be a number of one of the options!");
        }

        return Integer.parseInt(userSelect);
    }

    public MainLoop(Scanner sc){
        this.sc = sc;
    }

    public void startLoop(){
        boolean anotherOne = true;

        System.out.println(helloMessage);

        while (anotherOne) {
            try{
                System.out.println(prompt);

                int userSelect = selectOption();

                switch (userSelect){
                    case 1 -> anotherOne = false;
                    default -> throw new InputMismatchException("Your pick must a number of one of the options!");
                }

            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
