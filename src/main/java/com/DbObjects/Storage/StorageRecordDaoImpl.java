package com.DbObjects.Storage;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Dao;
import com.DbObjects.Plant.Plant;

import java.sql.*;
import java.util.ArrayList;

public class StorageRecordDaoImpl implements StorageRecordDao {
    @Override
    public ArrayList<StorageRecord> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<StorageRecord> storageRecords = new ArrayList<>();
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String sqlSelectAll = "SELECT * FROM Stored_plants;";

        try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
            ResultSet rs = psSelectAll.executeQuery();

            while(rs.next()){
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

                StorageRecord storageRecord = new StorageRecord(packaging, plant);
                storageRecords.add(storageRecord);
            }
        }

        conn.close();

        return storageRecords;
    }

    @Override
    public void insert(StorageRecord item) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String sqlGetPlantId = "SELECT id FROM Plant WHERE name = ?;";
        int plantId = -1;
        Plant plant = item.getPlant();

        try(PreparedStatement psGetPlantId = conn.getConnection().prepareStatement(sqlGetPlantId)){
            psGetPlantId.setString(1, plant.getName());

            ResultSet rs = psGetPlantId.executeQuery();

            if(rs.next()){
                plantId = rs.getInt("id");
            }
        }

        String sqlInsertPackaging = "INSERT INTO Packaging(expiration_date, number_of_seeds) " +
                "VALUES (?, ?);";
        int packagingId = -1;
        Packaging packaging = item.getPackaging();

        try(PreparedStatement psInsertPackaging = conn.getConnection().prepareStatement(sqlInsertPackaging, Statement.RETURN_GENERATED_KEYS)){
            psInsertPackaging.setDate(1, packaging.getExpirationDate());
            psInsertPackaging.setInt(2, packaging.getNumberOfSeeds());

            psInsertPackaging.execute();

            try(ResultSet generatedKeys = psInsertPackaging.getGeneratedKeys()){
                if (generatedKeys.next()){
                    packagingId = generatedKeys.getInt(1);
                }
            }
        }

        String sqlStorageInsert = "INSERT INTO Storage(plant_id, packaging_id) " +
                "VALUES (?, ?);";

        try(PreparedStatement psStorageInsert = conn.getConnection().prepareStatement(sqlStorageInsert)){
            psStorageInsert.setInt(1, plantId);
            psStorageInsert.setInt(2, packagingId);

            psStorageInsert.execute();
        }

        conn.close();
    }

    @Override
    public void updateNumberOfSeeds(StorageRecord storageRecord) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();



        conn.close();
    }
}
