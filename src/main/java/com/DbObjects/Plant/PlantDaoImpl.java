package com.DbObjects.Plant;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlantDaoImpl implements Dao<Plant> {
    @Override
    public ArrayList<Plant> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<Plant> plants = new ArrayList<>();
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String sqlSelectAll = "SELECT * FROM Plant;";

        try(PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)){
            ResultSet rs = psSelectAll.executeQuery();

            while(rs.next()){
                String plantName = rs.getString("name");
                Plant.plantType plantLifeLength = Plant.plantType.valueOf(rs.getString("life_length"));
                int plantGrowingTime = rs.getInt("growing_time");
                int plantSpacing = rs.getInt("spacing");
                int plantPlantingDepth = rs.getInt("planting_depth");
                int plantPlantingTime = rs.getInt("planting_time");
                boolean plantPreGrowing = rs.getBoolean("pre_growing");
                Plant plant = new Plant(plantName,
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
    }

    @Override
    public void insert(Plant item) {

    }
}
