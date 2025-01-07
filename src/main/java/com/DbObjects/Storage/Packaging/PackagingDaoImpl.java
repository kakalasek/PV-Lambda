package com.DbObjects.Storage.Packaging;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;

import java.sql.SQLException;

public class PackagingDaoImpl implements PackagingDao{
    @Override
    public Packaging find(int id) {
        return null;
    }

    @Override
    public void insert(Packaging item) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {

    }

    @Override
    public void update(Packaging item) {

    }

    @Override
    public void delete(int id) {

    }
}
