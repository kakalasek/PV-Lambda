package com.DbObjects.Storage.Packaging;

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

    }

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
            throw new RuntimeException(e);
        }
    }
}
