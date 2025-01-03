package com.MainLoop.Select;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Storage.StorageRecordDaoImpl;

import java.sql.SQLException;

public class Selector {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    public String selectStorageRecords() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        return storageRecordDao.getAll().toString();
    }
}
