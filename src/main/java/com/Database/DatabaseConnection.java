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

    /**
     * Creates the connection using data from config.properties file.
     * @throws LoadingPropertiesException Gets thrown if any problem occurs when trying to read the properties file.
     */
    public DatabaseConnection() throws LoadingPropertiesException {
        try{
            Properties prop = new Properties();

            FileInputStream ip = new FileInputStream("config.properties");

            prop.load(ip);

            this.url = prop.getProperty("url");
            this.username = prop.getProperty("username");
            this.password = prop.getProperty("password");

        } catch (IOException e){
            throw new LoadingPropertiesException("Loading properties failed:\n" + e);
        }
    }

    /**
     * Used to retrieve the current connection object in order to perform some actions on it
     * @return The Connection object
     * @throws CouldNotEstablishConnectionException Gets thrown if no active connection has been established for this DatabaseConnection object
     */
    public Connection getConnection() throws CouldNotEstablishConnectionException {
        if (connection == null) throw new CouldNotEstablishConnectionException("No connection was established for this session");
        return connection;
    }

    /**
     * Creates an active connection for this DatabaseConnection object
     * @throws SQLException If anything goes wrong with creating the connection
     */
    public void connect() throws SQLException, CouldNotEstablishConnectionException {
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes and active connection which was established for this DatabaseConnection object
     * @throws CouldNotEstablishConnectionException Gets thrown no active connection has been established for this DatabaseConnection object
     * @throws SQLException If anything goes wrong with closing the connection
     */
    public void close() throws CouldNotEstablishConnectionException, SQLException {
        if (connection == null) throw new CouldNotEstablishConnectionException("No connection was established for this session");
        connection.close();
    }

}