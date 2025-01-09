package com.MainLoop;

import com.CustomExceptions.InvalidOptionException;
import com.CustomExceptions.NumberNotWithinOptionsException;
import com.MainLoop.ChangeIsolationLevel.ChangeIsolationLevelCommand;
import com.MainLoop.Delete.DeleteStorageRecordCommand;
import com.MainLoop.Examples.NonRepeatableReadExampleCommand;
import com.MainLoop.Examples.PhantomReadExampleCommand;
import com.MainLoop.GenerateReport.GenerateReportCommand;
import com.MainLoop.ImportFromFile.ImportFromCsvCommand;
import com.MainLoop.Insert.InsertPlantingCommand;
import com.MainLoop.Insert.InsertStorageRecordCommand;
import com.MainLoop.Menu.Menu;
import com.MainLoop.Select.SelectPlantingsCommand;
import com.MainLoop.Select.SelectStorageRecordsCommand;
import com.MainLoop.Update.LiquidatePlantCommand;
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
    private final Menu menu;

    public MainLoop(){
        this.sc = ScannerWrapper.getScanner();
        this.menu = new Menu();
    }

    public int selectOption() throws InvalidOptionException {
        String userSelect = sc.nextLine();

        if (userSelect.equalsIgnoreCase("end")) return -1;

        if (!InputChecker.isPositiveNumber(userSelect)) throw new InvalidOptionException("Your pick must be a number of one of the options!");

        return Integer.parseInt(userSelect);
    }

    private void registerMenuItems(){
        menu.registerItem("Add Seeds To Storage", new InsertStorageRecordCommand());
        menu.registerItem("Plant Seeds", new InsertPlantingCommand());
        menu.registerItem("Remove Seeds From Storage", new DeleteStorageRecordCommand());
        menu.registerItem("Liquidate Plants", new LiquidatePlantCommand());
        menu.registerItem("Show Stored Seeds", new SelectStorageRecordsCommand());
        menu.registerItem("Show Plantings", new SelectPlantingsCommand());
        menu.registerItem("Change DB Isolation Level", new ChangeIsolationLevelCommand());
        menu.registerItem("Generate Report", new GenerateReportCommand());
        menu.registerItem("Import Plants From File", new ImportFromCsvCommand());
        menu.registerItem("Non-Repeatable Read", new NonRepeatableReadExampleCommand());
        menu.registerItem("Phantom Read", new PhantomReadExampleCommand());
    }

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

            } catch (InvalidOptionException | NumberNotWithinOptionsException e){
                System.out.println(e.getMessage());
            }
        }

        ScannerWrapper.closeScanner();
    }
}
