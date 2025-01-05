package com.DbObjects.Storage;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Dao;

import java.sql.SQLException;

public interface StorageRecordDao extends Dao<StorageRecord> {
    void updateNumberOfSeeds(StorageRecord storageRecord, int newNumberOfSeeds) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException;
}
