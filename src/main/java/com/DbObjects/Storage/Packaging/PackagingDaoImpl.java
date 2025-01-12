package com.DbObjects.Storage.Packaging;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackagingDaoImpl implements PackagingDao{
    @Override
    public Packaging find(int id) {
        return null;
    }

    @Override
    public void insert(Packaging item) {

    }

    @Override
    public void update(Packaging item) {

    }

    @Override
    public void delete(int id) {
        try{
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlDelete = " CALL remove_seeds(?);";

            try(PreparedStatement psDelete = conn.getConnection().prepareStatement(sqlDelete)){
                psDelete.setInt(1, id);

                psDelete.execute();
            }

            conn.close();
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem deleting the storage record", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Finds a packaging by its storage records id. Since every packaging can be assigned only to one storage record, it just works
     * @param id The id of some storage record
     * @return The Packaging object with all of its attributes
     */
    @Override
    public Packaging findByStorageRecordId(int id) {
        try{
            Packaging packaging = null;
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlFindByStorageRecordId = "SELECT id, expiration_date, number_of_seeds " +
                    "FROM Packaging " +
                    "WHERE id = (SELECT packaging_id FROM Storage WHERE id = ?);";

            try (PreparedStatement psFindByStorageRecordId = conn.getConnection().prepareStatement(sqlFindByStorageRecordId)){
                 psFindByStorageRecordId.setInt(1, id);

                ResultSet rs = psFindByStorageRecordId.executeQuery();

                if (rs.next()) {
                    int packagingId = rs.getInt("id");
                    Date expirationDate = rs.getDate("expiration_date");
                    int numberOfSeeds = rs.getInt("number_of_seeds");

                    packaging = new Packaging(packagingId, expirationDate, numberOfSeeds);
                }

            }

            conn.close();

            return packaging;
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem find your packaging by the storage record id", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
