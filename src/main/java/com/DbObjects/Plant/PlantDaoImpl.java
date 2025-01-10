package com.DbObjects.Plant;

import com.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlantDaoImpl implements PlantDao {
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
            throw new RuntimeException("There has been a problem with executing your query");
        }
    }

    @Override
    public Plant findByName(String name) {
        try{
            Plant plant = null;
            DatabaseConnection conn = new DatabaseConnection();
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
            }

            conn.close();

            return plant;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
