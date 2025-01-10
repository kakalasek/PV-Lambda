package com.DbObjects.Storage.StorageRecord;

import com.DbObjects.Dao;

import java.util.ArrayList;

public interface StorageRecordDao extends Dao<StorageRecord> {
    void updateNumberOfSeeds(StorageRecord storageRecord, int newNumberOfSeeds);
    ArrayList<StorageRecord> findAll();
}
