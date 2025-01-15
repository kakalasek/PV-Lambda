package com.Database;

import com.CustomExceptions.ConnectionException;
import com.CustomExceptions.InvalidIsolationLevelException;
import com.CustomExceptions.LoadingPropertiesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * This class serves as a connection do the database. It provides basic methods to interact with the database.
 */
public class DatabaseConnection{

    private final String url;
    private final String username;
    private final String password;
    private Connection connection;
    private static int transactionIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * Creates the connection using data from config.properties file.
     * @throws LoadingPropertiesException Gets thrown if any problem occurs when trying to read the properties file.
     */
    public DatabaseConnection(){
        try{
            Properties prop = new Properties();

            FileInputStream ip = new FileInputStream("config.properties");

            prop.load(ip);

            this.url = prop.getProperty("URL");
            this.username = prop.getProperty("USERNAME");
            this.password = prop.getProperty("PASSWORD");

        } catch (IOException e){
            throw new LoadingPropertiesException("Loading properties failed", e);
        } catch (ConnectionException e){
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Used to retrieve the current connection object in order to perform some actions on it
     * @return The Connection object
     * @throws ConnectionException Gets thrown if no active connection has been established for this DatabaseConnection object
     */
    public Connection getConnection(){
        if (connection == null) throw new ConnectionException("No connection was established for this session");
        return connection;
    }

    /**
     * Simply tells if this database connection has established a connection or not
     * @return True if a connection was established, False if not
     */
    public boolean isConnected(){
        return connection != null;
    }

    /**
     * Creates an active connection for this DatabaseConnection object
     * Note that if this DatabaseConnection already has an active connection, this method does nothing
     * Also note, that is sets the default connection database transaction isolation level to REPEATABLE_READ
     * @throws ConnectionException Gets thrown if the connection fails for any reason
     */
    public void connect() {
        try {
            if (connection == null){
                connection = DriverManager.getConnection(url, username, password);
                connection.setTransactionIsolation(transactionIsolationLevel);
            }
        } catch (SQLException e){
            throw new ConnectionException("There has been a problem connecting to the database", e);
        }
    }

    /**
     * Closes and active connection which was established for this DatabaseConnection object
     * @throws ConnectionException Gets thrown no active connection has been established for this DatabaseConnection object
     */
    public void close() {
        try {
            if (connection == null)
                throw new ConnectionException("No connection was established for this session");
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException("There has been a problem closing your connection", e);
        }
    }

    /**
     * Changes the default database transaction isolation level for all subsequently created connections
     * Note that not every
     * @param isolationLevel An int identifier for one of the four transaction isolation levels defined
     *                       in Connection class. They are 1,2,4 and 8. From READ UNCOMMITED to SERIALIZABLE
     *                       respectively. You can find more information on the internet.
     * @throws InvalidIsolationLevelException Gets thrown if you provided a non-existent database transaction
     *                                        isolation level
     */
    public static void setTransactionIsolationLevel(int isolationLevel){
        boolean isAnIsolationLevel = false;
        int[] isolationLevels = {Connection.TRANSACTION_READ_UNCOMMITTED,
        Connection.TRANSACTION_READ_COMMITTED, Connection.TRANSACTION_REPEATABLE_READ, Connection.TRANSACTION_SERIALIZABLE};

        for(int isolationLevelLocal : isolationLevels){
            if (isolationLevelLocal == isolationLevel){
                isAnIsolationLevel = true;
                break;
            }
        }

        if (isAnIsolationLevel) transactionIsolationLevel = isolationLevel;
        else throw new InvalidIsolationLevelException("This isolation level does not exist");
    }
}