package com.DbObjects.Planting;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Dao;
import com.DbObjects.Plant.Plant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class PlantingDaoImpl implements Dao<Planting> {
    @Override
    public ArrayList<Planting> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<Planting> plantings = new ArrayList<>();
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String sqlSelectAll = "SELECT * FROM Planted_plants";

        try(PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)){
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

                double flowerbedSize = rs.getDouble("Flowerbed_Size");
                int flowerbedNumber = rs.getInt("Flowerbed_Number");
                Flowerbed flowerbed = new Flowerbed(flowerbedSize, flowerbedNumber);

                Date dateFrom = rs.getDate("Date_Of_Planting");
                Date dateTo = rs.getDate("Date_Of_Disposal");
                int numberOfSeeds = rs.getInt("Number_Of_Seeds");

                Planting planting = new Planting(dateFrom, dateTo, numberOfSeeds, flowerbed, plant);
                plantings.add(planting);
            }
        }

        conn.close();

        return plantings;
    }

    @Override
    public void insert(Planting item) {

    }
}
