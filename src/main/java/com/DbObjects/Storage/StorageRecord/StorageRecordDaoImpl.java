package com.DbObjects.Storage.StorageRecord;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;

import java.sql.*;
import java.util.ArrayList;

public class StorageRecordDaoImpl implements StorageRecordDao {
    @Override
    public ArrayList<StorageRecord> findAll() {
        try {
            ArrayList<StorageRecord> storageRecords = new ArrayList<>();
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Stored_plants;";

            try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
                ResultSet rs = psSelectAll.executeQuery();

                while (rs.next()) {
                    String plantName = rs.getString("Plant_Name");
                    Plant.plantType plantLifeLength = Plant.plantType.valueOf(rs.getString("Life_Length"));
                    int plantGrowingTime = rs.getInt("Time_To_Grow");
                    int plantSpacing = rs.getInt("Spacing");
                    int plantPlantingDepth = rs.getInt("Depth_Of_Planting");
                    int plantPlantingTime = rs.getInt("Month_To_Plant");
                    boolean plantPreGrowing = rs.getBoolean("Pre_Growing");
                    Plant plant = new Plant(plantName,
                            plantLifeLength,
                            plantGrowingTime,
                            plantSpacing,
                            plantPlantingDepth,
                            plantPlantingTime,
                            plantPreGrowing);

                    Date packagingExpirationDate = rs.getDate("Expiration_Date");
                    int packagingNumberOfSeeds = rs.getInt("Number_Of_Seeds_In_Package");
                    Packaging packaging = new Packaging(packagingExpirationDate, packagingNumberOfSeeds);

                    int id = rs.getInt("Stored_Plant_id");

                    StorageRecord storageRecord = new StorageRecord(id, packaging, plant);
                    storageRecords.add(storageRecord);
                }
            }

            conn.close();

            return storageRecords;
        } catch (SQLException e) {
            throw new RuntimeException("There has been a problem with your query");
        }
    }

    @Override
    public StorageRecord find(int id) {
        return null;
    }

    @Override
    public void insert(StorageRecord item) {
        try {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlGetPlantId = "SELECT id FROM Plant WHERE name = ?;";
            int plantId = -1;
            Plant plant = item.getPlant();

            try (PreparedStatement psGetPlantId = conn.getConnection().prepareStatement(sqlGetPlantId)) {
                psGetPlantId.setString(1, plant.getName());

                ResultSet rs = psGetPlantId.executeQuery();

                if (rs.next()) {
                    plantId = rs.getInt("id");
                }
            }

            String sqlInsertPackaging = "INSERT INTO Packaging(expiration_date, number_of_seeds) " +
                    "VALUES (?, ?);";
            int packagingId = -1;
            Packaging packaging = item.getPackaging();

            try (PreparedStatement psInsertPackaging = conn.getConnection().prepareStatement(sqlInsertPackaging, Statement.RETURN_GENERATED_KEYS)) {
                psInsertPackaging.setDate(1, packaging.getExpirationDate());
                psInsertPackaging.setInt(2, packaging.getNumberOfSeeds());

                psInsertPackaging.execute();

                try (ResultSet generatedKeys = psInsertPackaging.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        packagingId = generatedKeys.getInt(1);
                    }
                }
            }

            String sqlStorageInsert = "INSERT INTO Storage(plant_id, packaging_id) " +
                    "VALUES (?, ?);";

            try (PreparedStatement psStorageInsert = conn.getConnection().prepareStatement(sqlStorageInsert)) {
                psStorageInsert.setInt(1, plantId);
                psStorageInsert.setInt(2, packagingId);

                psStorageInsert.execute();
            }

            conn.close();

        } catch (Exception e){
            throw new RuntimeException("There has been a problem with your query");
        }
    }

    @Override
    public void update(StorageRecord item) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void updateNumberOfSeeds(StorageRecord storageRecord, int newNumberOfSeeds) {
        try {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlUpdateNumberOfSeeds = "CALL plant_seeds(?, ?);";

            System.out.println(storageRecord.getPackaging().getId());
            System.out.println(newNumberOfSeeds);

            try (PreparedStatement psUpdateNumberOfSeeds = conn.getConnection().prepareStatement(sqlUpdateNumberOfSeeds)) {
                psUpdateNumberOfSeeds.setInt(1, storageRecord.getPackaging().getId());
                psUpdateNumberOfSeeds.setInt(2, newNumberOfSeeds);

                psUpdateNumberOfSeeds.execute();
            }

            conn.close();
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem with your query");
        }
    }
}
