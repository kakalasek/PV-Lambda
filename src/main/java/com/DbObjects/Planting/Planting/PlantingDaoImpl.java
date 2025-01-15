package com.DbObjects.Planting.Planting;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Flowerbed.Flowerbed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class PlantingDaoImpl implements PlantingDao {

    /**
     * Retrieves all plantings from the database.
     * NOTE that this method uses a view in order to return all the plantings. This view does not contain
     * ids of Flowerbed or Plant, so they will be set to 0 by default
     * @return The ArrayList of plantings
     */
    @Override
    public ArrayList<Planting> findAll(){
        DatabaseConnection conn = new DatabaseConnection();
        try {
            ArrayList<Planting> plantings = new ArrayList<>();
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
            } catch (Exception e){
                throw new SQLException(e);
            }


            return plantings;
        } catch (SQLException e){
            throw new RuntimeException("The has been a problem when selecting all the plantings from the database", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    /**
     * Retrieves all plantings from the database, which were not yet liquidated.
     * NOTE that this method uses a view in order to return all the plantings. This view does not contain
     * ids of Flowerbed or Plant, so they will be set to 0 by default
     * @return The ArrayList of plantings, which were not yet liquidated
     */
    @Override
    public ArrayList<Planting> findAllPlanted() {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            ArrayList<Planting> plantings = new ArrayList<>();
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
            } catch (Exception e){
                throw new SQLException(e);
            }


            return plantings;
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem selecting all the planted plants", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    /**
     * Liquidates a plant by updating its dateTo column
     * @param id The id of the Planting you want to liquidate
     * @param liquidationDate The liquidation date
     */
    @Override
    public void liquidatePlanting(int id, Date liquidationDate) {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            conn.connect();

            String sqlLiquidatePlanting = "UPDATE Planting SET date_to = ? WHERE id = ?;";

            try (PreparedStatement psInsertPlanting = conn.getConnection().prepareStatement(sqlLiquidatePlanting)) {
                psInsertPlanting.setDate(1, liquidationDate);
                psInsertPlanting.setInt(2, id);

                psInsertPlanting.execute();
            } catch (Exception e){
                throw new SQLException(e);
            }


        } catch (SQLException e){
            throw new RuntimeException("There has been a problem liquidating the planting", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    @Override
    public Planting find(int id) {
        return null;
    }

    /**
     * Inserts a new planting
     * @param item The planting you want to insert
     */
    @Override
    public void insert(Planting item){
        DatabaseConnection conn = new DatabaseConnection();
        try {
            conn.connect();

            String sqlInsertPlanting = "INSERT INTO Planting(date_from, number_of_seeds, plant_id, flowerbed_id) " +
                    "VALUES (?, ?, ?, ?);";

            try (PreparedStatement psInsertPlanting = conn.getConnection().prepareStatement(sqlInsertPlanting)) {
                psInsertPlanting.setDate(1, item.getDateFrom());
                psInsertPlanting.setInt(2, item.getNumberOfSeeds());
                psInsertPlanting.setInt(3, item.getPlant().getId());
                psInsertPlanting.setInt(4, item.getFlowerbed().getId());

                psInsertPlanting.execute();

            } catch (Exception e){
                throw new SQLException(e);
            }

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem inserting the planting", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    @Override
    public void update(Planting item) {

    }

    @Override
    public void delete(int id) {

    }
}
