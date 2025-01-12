package com.Commands.Examples;

import com.Database.DatabaseConnection;
import com.Commands.Command;

import java.sql.Connection;

/**
 * Command which provides a simple example of the Non-Repeatable read phenomena
 */
public class NonRepeatableReadExampleCommand implements Command {
    @Override
    public void execute() {
        System.out.println("This is an example showing the concept of Non-Repeatable Read");

        DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);



        DatabaseConnection.setTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ);
    }
}
