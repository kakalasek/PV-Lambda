package com.DbObjects.Planting.Planting;

import com.Database.DatabaseConnection;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Flowerbed.Flowerbed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class PlantingDaoImpl implements PlantingDao {
    @Override
    public ArrayList<Planting> findAll(){
        try {
            ArrayList<Planting> plantings = new ArrayList<>();
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Planted_plants";

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

                    double flowerbedSize = rs.getDouble("Flowerbed_Size");
                    int flowerbedNumber = rs.getInt("Flowerbed_Number");
                    Flowerbed flowerbed = new Flowerbed(flowerbedSize, flowerbedNumber);

                    int plantingId = rs.getInt("Planting_id");
                    Date dateFrom = rs.getDate("Date_Of_Planting");
                    Date dateTo = rs.getDate("Date_Of_Disposal");
                    int numberOfSeeds = rs.getInt("Number_Of_Seeds");

                    Planting planting = new Planting(plantingId, dateFrom, dateTo, numberOfSeeds, flowerbed, plant);
                    plantings.add(planting);
                }
            }

            conn.close();

            return plantings;
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem executing your query");
        }
    }

    @Override
    public ArrayList<Planting> findAllPlanted() {
        try {
            ArrayList<Planting> plantings = new ArrayList<>();
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Planted_plants WHERE Date_Of_Disposal IS NULL;";

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

                    double flowerbedSize = rs.getDouble("Flowerbed_Size");
                    int flowerbedNumber = rs.getInt("Flowerbed_Number");
                    Flowerbed flowerbed = new Flowerbed(flowerbedSize, flowerbedNumber);

                    int plantingId = rs.getInt("Planting_id");
                    Date dateFrom = rs.getDate("Date_Of_Planting");
                    Date dateTo = rs.getDate("Date_Of_Disposal");
                    int numberOfSeeds = rs.getInt("Number_Of_Seeds");

                    Planting planting = new Planting(plantingId, dateFrom, dateTo, numberOfSeeds, flowerbed, plant);
                    plantings.add(planting);
                }
            }

            conn.close();

            return plantings;
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem executing your query");
        }
    }

    @Override
    public void liquidatePlanting(int id, Date liquidationDate) {
        try {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlLiquidatePlanting = "UPDATE Planting SET date_to = ? WHERE id = ?;";

            try (PreparedStatement psInsertPlanting = conn.getConnection().prepareStatement(sqlLiquidatePlanting)) {
                psInsertPlanting.setDate(1, liquidationDate);
                psInsertPlanting.setInt(2, id);

                psInsertPlanting.execute();
            }

            conn.close();

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem executing the query");
        }
    }

    @Override
    public Planting find(int id) {
        return null;
    }

    @Override
    public void insert(Planting item){
        try {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlInsertPlanting = "INSERT INTO Planting(date_from, number_of_seeds, plant_id, flowerbed_id) " +
                    "VALUES (?, ?, ?, ?);";

            try (PreparedStatement psInsertPlanting = conn.getConnection().prepareStatement(sqlInsertPlanting)) {
                psInsertPlanting.setDate(1, item.getDateFrom());
                psInsertPlanting.setInt(2, item.getNumberOfSeeds());
                psInsertPlanting.setInt(3, item.getPlant().getId());
                psInsertPlanting.setInt(4, item.getFlowerbed().getId());

                psInsertPlanting.execute();

            }

            conn.close();

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem executing the query");
        }
    }

    @Override
    public void update(Planting item) {

    }

    @Override
    public void delete(int id) {

    }
}
