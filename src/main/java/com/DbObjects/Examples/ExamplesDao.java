package com.DbObjects.Examples;

import com.DbObjects.Storage.Packaging.Packaging;

import java.sql.Connection;

/**
 * This Database Access Object interface is meant specifically for methods
 * used only by the example commands
 */
public interface ExamplesDao {
    int readNumberOfSeedsInPackaging(int id, Connection connection);
    void writeNumberOfSeedsInPackaging(int id, int newNumberOfSeeds, Connection connection);
    int insertPackagingAndRetrieveId(Packaging packaging, Connection connection);
    void deletePackaging(int id, Connection connection);
}
