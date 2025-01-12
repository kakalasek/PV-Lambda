package com.Commands.Examples;

import com.Commands.Command;
import com.Database.DatabaseConnection;
import com.DbObjects.Examples.ExamplesDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;
import de.vandermeer.asciitable.AsciiTable;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Command which provides a simple example of the Phantom Read phenomena
 */
public class PhantomReadExampleCommand implements Command {

    ExamplesDaoImpl examplesDao = new ExamplesDaoImpl();

    private String generatePackagingTable(ArrayList<Packaging> packaging){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("id", "Expiration Date", "Number Of Seeds");
        table.addRule();

        for (Packaging currentPackaging : packaging) {
            table.addRow(currentPackaging.getId(), currentPackaging.getExpirationDate(), currentPackaging.getNumberOfSeeds());
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try {
            System.out.println("This is an example showing the concept of Phantom Read\n");

            DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
            DatabaseConnection helperConnection = new DatabaseConnection();
            DatabaseConnection connection1 = new DatabaseConnection();
            DatabaseConnection connection2 = new DatabaseConnection();

            connection1.connect();
            connection2.connect();

            connection1.getConnection().setAutoCommit(false);
            connection2.getConnection().setAutoCommit(false);

            System.out.println("Connection 1 is going to read all the rows from the packaging table");
            ArrayList<Packaging> packaging = examplesDao.readAllPackaging(connection1.getConnection());
            String renderedTable = generatePackagingTable(packaging);
            System.out.println(renderedTable);
            System.out.println();

            System.out.println("While connection 1 is doing something with the returned rows, connection 2 will add some new rows and commit\n");
            int packagingId = examplesDao.insertPackagingAndRetrieveId(new Packaging(Date.valueOf("2025-06-07"), 50), connection2.getConnection());
            connection2.getConnection().commit();
            connection2.close();

            System.out.println("Connection 1 will now look at the rows to check again, if the have not changed. It will find out that there is one more row");
            packaging = examplesDao.readAllPackaging(connection1.getConnection());
            renderedTable = generatePackagingTable(packaging);
            System.out.println(renderedTable);

            connection1.getConnection().commit();
            connection1.close();

            helperConnection.connect();

            examplesDao.deletePackaging(packagingId, helperConnection.getConnection());

            helperConnection.close();

            DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
