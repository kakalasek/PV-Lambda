package com.DbObjects.Storage.StorageRecord;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StorageRecordDao extends Dao<StorageRecord> {
    void updateNumberOfSeeds(StorageRecord storageRecord, int newNumberOfSeeds);
    ArrayList<StorageRecord> findAll();
}
