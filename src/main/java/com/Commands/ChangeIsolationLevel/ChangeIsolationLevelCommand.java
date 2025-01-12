package com.Commands.ChangeIsolationLevel;

import com.Database.DatabaseConnection;
import com.Commands.Command;
import com.utils.HandyTools.HandyTools;
import de.vandermeer.asciitable.AsciiTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Command which displays which lets the user change the database isolation level of created connection
 * for this program session
 */
public class ChangeIsolationLevelCommand implements Command {

    /**
     * Will generate a simple table of database isolation levels and their choice indexes
     * @param databaseIsolationLevels A list of database isolation level names
     * @return The rendered table string
     */
    private String generateDatabaseIsolationTable(ArrayList<String> databaseIsolationLevels){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Choice", "Isolation Level");
        table.addRule();

        for (int i = 0; i < databaseIsolationLevels.size(); i++){
            table.addRow(i, databaseIsolationLevels.get(i));
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try{
            ArrayList<String> databaseIsolationLevels = new ArrayList<>(Arrays.asList("READ_UNCOMMITED", "READ_COMMITED", "REPEATABLE_READ", "SERIALIZABLE"));
            int[] databaseIsolationLevelsValues = {Connection.TRANSACTION_READ_UNCOMMITTED, Connection.TRANSACTION_READ_COMMITTED, Connection.TRANSACTION_REPEATABLE_READ, Connection.TRANSACTION_SERIALIZABLE};

            String renderedTable = generateDatabaseIsolationTable(databaseIsolationLevels);

            System.out.println(renderedTable);

            int databaseIsolationLevelPick = HandyTools.chooseFromList("Choose database isolation level by its index", databaseIsolationLevels.size());

            DatabaseConnection.setTransactionIsolationLevel(databaseIsolationLevelsValues[databaseIsolationLevelPick]);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
