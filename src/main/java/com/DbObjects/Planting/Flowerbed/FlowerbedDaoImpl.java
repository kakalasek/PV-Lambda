package com.DbObjects.Planting.Flowerbed;

import com.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowerbedDaoImpl implements FlowerbedDao {
    @Override
    public ArrayList<Flowerbed> findAll() {
        try {
            ArrayList<Flowerbed> flowerbeds = new ArrayList<>();
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Flowerbed;";

            try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
                ResultSet rs = psSelectAll.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    double size = rs.getDouble("size");
                    int number = rs.getInt("number");
                    Flowerbed flowerbed = new Flowerbed(id, size, number);

                    flowerbeds.add(flowerbed);
                }
            }

            conn.close();

            return flowerbeds;

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem executing the select query", e);
        }
    }

    @Override
    public Flowerbed find(int id) {
        return null;
    }

    @Override
    public void insert(Flowerbed item) {

    }

    @Override
    public void update(Flowerbed item) {

    }

    @Override
    public void delete(int id) {

    }

}
