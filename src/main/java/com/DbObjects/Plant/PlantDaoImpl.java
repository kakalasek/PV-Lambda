package com.DbObjects.Plant;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlantDaoImpl implements PlantDao {

    /**
     * Selects all plants from the database
     * @return ArrayList of all the plants in the database
     */
    @Override
    public ArrayList<Plant> findAll(){
        try {
            ArrayList<Plant> plants = new ArrayList<>();
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Plant;";

            try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
                ResultSet rs = psSelectAll.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String plantName = rs.getString("name");
                    Plant.plantType plantLifeLength = Plant.plantType.valueOf(rs.getString("life_length"));
                    int plantGrowingTime = rs.getInt("growing_time");
                    int plantSpacing = rs.getInt("spacing");
                    int plantPlantingDepth = rs.getInt("planting_depth");
                    int plantPlantingTime = rs.getInt("planting_time");
                    boolean plantPreGrowing = rs.getBoolean("pre_growing");
                    Plant plant = new Plant(id ,plantName,
                            plantLifeLength,
                            plantGrowingTime,
                            plantSpacing,
                            plantPlantingDepth,
                            plantPlantingTime,
                            plantPreGrowing);

                    plants.add(plant);
                }
            }

            conn.close();

            return plants;
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem selecting all the plants", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Selects a plant by its name
     * @param name The name of the plant
     * @return The Plant object with the provided name
     */
    @Override
    public Plant findByName(String name) {
        DatabaseConnection conn = new DatabaseConnection();
        try{
            Plant plant = null;
            conn.connect();

            String sqlSelectByName = "SELECT * FROM Plant WHERE name = ?;";

            try(PreparedStatement psSelectByName = conn.getConnection().prepareStatement(sqlSelectByName)){
                psSelectByName.setString(1, name);

                ResultSet rs = psSelectByName.executeQuery();

                if(rs.next()) {
                    int id = rs.getInt("id");
                    String plantName = rs.getString("name");
                    Plant.plantType plantLifeLength = Plant.plantType.valueOf(rs.getString("life_length"));
                    int plantGrowingTime = rs.getInt("growing_time");
                    int plantSpacing = rs.getInt("spacing");
                    int plantPlantingDepth = rs.getInt("planting_depth");
                    int plantPlantingTime = rs.getInt("planting_time");
                    boolean plantPreGrowing = rs.getBoolean("pre_growing");

                    plant = new Plant(id ,plantName,
                            plantLifeLength,
                            plantGrowingTime,
                            plantSpacing,
                            plantPlantingDepth,
                            plantPlantingTime,
                            plantPreGrowing);

                }
            } catch (Exception e){
                throw new SQLException(e);
            }


            return plant;
        } catch (SQLException e) {
            throw new RuntimeException("There has been a problem selecting the plant by name", e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    @Override
    public Plant find(int id) {
        return null;
    }

    @Override
    public void insert(Plant item) {

    }

    @Override
    public void update(Plant item) {

    }

    @Override
    public void delete(int id) {

    }
}
