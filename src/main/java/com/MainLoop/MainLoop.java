package com.MainLoop;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.MainLoop.Insert.Inserter;
import com.MainLoop.Select.Selector;

import java.sql.SQLException;
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
            | 1) Add Seeds To Storage        |
            | 2) Remove Seeds From Storage   |
            | 3) Plant Seeds                 |
            | 4) Liquidate Plants            |
            | 5) Show Stored Seeds           |
            | 6) Show Plantings              |
            | 7) Change Database Isolation   |
            |    Level                       |
            | 8) Non-Repeatable Read         |
            |     Example                    |
            | 9) Phantom Read Example        |
            | 10) Generate Report            |
            | 11) Import Plants from file    |
            | 0) Exit                        |
            +--------------------------------+
            
            """;

    private final Scanner sc;
    private final Selector selector;
    private final Inserter inserter;

    public int selectOption(){
        String userSelect = sc.nextLine();

        if (!userSelect.matches("\\d+")){
            throw new InputMismatchException("Your pick must be a number of one of the options!");
        }

        return Integer.parseInt(userSelect);
    }

    public MainLoop(){
        this.sc = new Scanner(System.in);
        this.selector = new Selector();
        this.inserter = new Inserter(sc);
    }

    public void startLoop(){
        boolean anotherOne = true;

        System.out.println(helloMessage);

        while (anotherOne) {
            try{
                System.out.println(prompt);

                int userSelect = selectOption();

                switch (userSelect){
                    case 0 -> anotherOne = false;
                    case 1 -> System.out.println(inserter.insertStorageRecord());
                    case 2 -> System.out.println("Remove seeds from storage");
                    case 3 -> System.out.println(inserter.insertPlanting());
                    case 4 -> System.out.println("Liquidate plants");
                    case 5 -> System.out.println(selector.selectStorageRecords());
                    case 6 -> System.out.println(selector.selectPlantings());
                    case 7 -> System.out.println("Change database isolation level");
                    case 8 -> System.out.println("Non-Repeatable read example");
                    case 9 -> System.out.println("Phantom Read example");
                    case 10 -> System.out.println("Report Generated");
                    case 11 -> System.out.println("Plants imported from file");
                    default -> throw new InputMismatchException("Your pick must a number of one of the options!");
                }

            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
            } catch (LoadingPropertiesException | CouldNotEstablishConnectionException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
