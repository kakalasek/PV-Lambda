package com.Commands.ChangeIsolationLevel;

import com.Database.DatabaseConnection;
import com.Commands.Command;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Connection;
import java.util.Scanner;

public class ChangeIsolationLevelCommand implements Command {
    @Override
    public void execute() {
        try{
            Scanner sc = ScannerWrapper.getScanner();

            String[] databaseIsolationLevels = {"READ_UNCOMMITED", "READ_COMMITED", "REPEATABLE_READ", "SERIALIZABLE"};
            int[] databaseIsolationLevelsValues = {Connection.TRANSACTION_READ_UNCOMMITTED, Connection.TRANSACTION_READ_COMMITTED, Connection.TRANSACTION_REPEATABLE_READ, Connection.TRANSACTION_SERIALIZABLE};

            for(int i = 0; i < databaseIsolationLevels.length; i++){
                System.out.println(i + " | " + databaseIsolationLevels[i]);
            }

            System.out.println("Choose database isolation level by its index");
            String isolationPickString = sc.nextLine();
            if(!InputChecker.isPositiveNumber(isolationPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }
            int isolationPick = Integer.parseInt(isolationPickString);
            if(isolationPick > databaseIsolationLevels.length){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            DatabaseConnection.setTransactionIsolationLevel(databaseIsolationLevelsValues[isolationPick]);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
