package com.DbObjects.Storage;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Plant.Plant;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StorageRecordDaoImpl implements StorageRecordDao{
    @Override
    public ArrayList<StorageRecord> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<StorageRecord> storageRecords = new ArrayList<>();
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String sqlSelectAll = "SELECT * FROM Stored_plants";

        try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
            ResultSet rs = psSelectAll.executeQuery();

            if(rs.next()){
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
}
