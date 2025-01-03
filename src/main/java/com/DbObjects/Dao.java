package com.DbObjects;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {
    ArrayList<T> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException;
}
