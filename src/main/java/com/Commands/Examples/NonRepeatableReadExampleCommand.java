package com.Commands.Examples;

import com.Database.DatabaseConnection;
import com.Commands.Command;
import com.DbObjects.Examples.ExamplesDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;

import java.sql.Connection;
import java.sql.Date;

/**
 * Command which provides a simple example of the Non-Repeatable read phenomena
 */
public class NonRepeatableReadExampleCommand implements Command {

    ExamplesDaoImpl examplesDao = new ExamplesDaoImpl();

    @Override
    public void execute() {
        try {
            System.out.println("This is an example showing the concept of Non-Repeatable Read\n");

            DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
            DatabaseConnection helperConnection = new DatabaseConnection();
            DatabaseConnection connection1 = new DatabaseConnection();
            DatabaseConnection connection2 = new DatabaseConnection();

            helperConnection.connect();

            Packaging testPackaging = new Packaging(Date.valueOf("2026-05-04"), 20);
            int packagingId = examplesDao.insertPackagingAndRetrieveId(testPackaging, helperConnection.getConnection());

            connection1.connect();
            connection2.connect();

            connection1.getConnection().setAutoCommit(false);
            connection2.getConnection().setAutoCommit(false);

            System.out.println("Connection 1 is going to read a value from a row");

            int numberOfSeeds = examplesDao.readNumberOfSeedsInPackaging(packagingId, connection1.getConnection());
            System.out.println("This is the returned number of seeds: " + numberOfSeeds + "\n");

            System.out.println("While connection 1 is doing some work on the retrieved value, Connection 2 will overwrite it and commited\n");
            examplesDao.writeNumberOfSeedsInPackaging(packagingId, 10, connection2.getConnection());
            connection2.getConnection().commit();
            connection2.close();

            System.out.println("Connection 1 did the work and will now again look at the value to be sure, it was not changed");
            numberOfSeeds = examplesDao.readNumberOfSeedsInPackaging(packagingId, connection1.getConnection());
            System.out.println("This is the number of seeds now: " + numberOfSeeds);

            connection1.getConnection().commit();
            connection1.close();

            examplesDao.deletePackaging(packagingId, helperConnection.getConnection());

            helperConnection.close();

            DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
