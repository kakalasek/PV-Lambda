package com.DbObjects.Storage.Packaging;

import com.DbObjects.Dao;

public interface PackagingDao extends Dao<Packaging> {
    Packaging findByStorageRecordId(int id);
}
