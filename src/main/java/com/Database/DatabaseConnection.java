package com.Database;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;

import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class server as a connection do the database. It provides basic methods to interact with the database.
 */
public class DatabaseConnection {

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
            throw new LoadingPropertiesException("Loading properties failed:\n" + e);
        }
    }

    /**
     * Used to retrieve the current connection object in order to perform some actions on it
     * @return The Connection object
     * @throws CouldNotEstablishConnectionException Gets thrown if no active connection has been established for this DatabaseConnection object
     */
    public Connection getConnection(){
        if (connection == null) throw new CouldNotEstablishConnectionException("No connection was established for this session");
        return connection;
    }

    /**
     * Creates an active connection for this DatabaseConnection object
     */
    public void connect() {
        try {
            if (connection == null){
                connection = DriverManager.getConnection(url, username, password);
                connection.setTransactionIsolation(transactionIsolationLevel);
            }
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem connecting to the database", e);
        }
    }

    /**
     * Closes and active connection which was established for this DatabaseConnection object
     * @throws CouldNotEstablishConnectionException Gets thrown no active connection has been established for this DatabaseConnection object
     */
    public void close() {
        try {
            if (connection == null)
                throw new CouldNotEstablishConnectionException("No connection was established for this session");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("There has been a problem closing your connection", e);
        }
    }

    public static void setTransactionIsolationLevel(int isolationLevel){
        transactionIsolationLevel = isolationLevel;
    }
}