package com.Database;

import java.util.Properties;

/**
 * This class server as a connection do the database. It provides basic methods to interact with the database.
 */
public class Connection {

    private static Connection instance = null;
    private final String url;
    private final String username;
    private final String password;

    private Connection(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static Connection createConnection(){
        if(instance == null){
            Properties prop = new Properties();

        }
        return instance;
    }
}
