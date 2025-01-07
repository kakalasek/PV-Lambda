package com.DbObjects;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;

import java.sql.SQLException;

public interface Dao<T> {

    T find(int id);
    void insert(T item) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException;
    void update(T item);
    void delete(int id);
}
