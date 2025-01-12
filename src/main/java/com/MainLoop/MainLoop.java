package com.MainLoop;

import com.Commands.ImportFromFile.ImportStorageRecordsFromCsvCommand;
import com.CustomExceptions.InvalidOptionException;
import com.Commands.ChangeIsolationLevel.ChangeIsolationLevelCommand;
import com.Commands.Delete.DeleteStorageRecordCommand;
import com.Commands.Examples.NonRepeatableReadExampleCommand;
import com.Commands.Examples.PhantomReadExampleCommand;
import com.Commands.GenerateReport.GenerateReportCommand;
import com.Commands.Insert.InsertPlantingCommand;
import com.Commands.Insert.InsertStorageRecordCommand;
import com.MainLoop.Menu.Menu;
import com.Commands.Select.SelectPlantingsCommand;
import com.Commands.Select.SelectStorageRecordsCommand;
import com.Commands.Update.LiquidatePlantCommand;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.util.Scanner;

/**
 * This is the main loop of this program. It provides a textual interface to the user and handles input.
 * It also calls all the individual commands.
 */
public class MainLoop {

    private final String helloMessage = """
           
            Hello there, welcome to this simple interface to your garden database. What is it you'd like to do?
            
            """;

    private final Scanner sc;
    private final Menu menu;

    public MainLoop(){
        this.sc = ScannerWrapper.getScanner();
        this.menu = new Menu();
    }

    /**
     * This simple method verifies the input and checks for the 'end' word, if user wants to exit the program
     * @return An integer form of the user option
     * @throws InvalidOptionException If the options is invalid, e. i. it is not a number
     */
    public int selectOption() throws InvalidOptionException {
        String userSelect = sc.nextLine();

        if (userSelect.equalsIgnoreCase("end")) return -1;

        if (!InputChecker.isPositiveNumber(userSelect)) throw new InvalidOptionException("Your pick must be a number of one of the options!");

        return Integer.parseInt(userSelect);
    }

    /**
     * Is here to provide a single method, which registers all the menu items
     */
    private void registerMenuItems(){
        menu.registerItem("Add Seeds To Storage", new InsertStorageRecordCommand());
        menu.registerItem("Plant Seeds", new InsertPlantingCommand());
        menu.registerItem("Remove Seeds From Storage", new DeleteStorageRecordCommand());
        menu.registerItem("Liquidate Plants", new LiquidatePlantCommand());
        menu.registerItem("Show Stored Seeds", new SelectStorageRecordsCommand());
        menu.registerItem("Show Plantings", new SelectPlantingsCommand());
        menu.registerItem("Change DB Isolation Level", new ChangeIsolationLevelCommand());
        menu.registerItem("Generate Report", new GenerateReportCommand());
        menu.registerItem("Import Plants From File", new ImportStorageRecordsFromCsvCommand());
        menu.registerItem("Non-Repeatable Read", new NonRepeatableReadExampleCommand());
        menu.registerItem("Phantom Read", new PhantomReadExampleCommand());
    }

    /**
     * Does exactly what is says. Starts the event loop
     */
    public void startLoop(){
        System.out.println(helloMessage);

        registerMenuItems();
        String prompt = menu.buildMenuPrompt();

        while (true) {
            try{
                System.out.println(prompt);

                int userSelect = selectOption();

                if (userSelect == -1) break;

                menu.selectItem(userSelect);

            } catch (InvalidOptionException e){
                System.out.println(e.getMessage());
            }
        }

        ScannerWrapper.closeScanner();
    }
}
