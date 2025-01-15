package com.DbObjects.Planting.Flowerbed;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowerbedDaoImpl implements FlowerbedDao {

    /**
     * Selects all flowerbeds from the database
     * @return ArrayList of all the flowerbeds from the database
     */
    @Override
    public ArrayList<Flowerbed> findAll() {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            ArrayList<Flowerbed> flowerbeds = new ArrayList<>();
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
            } catch (Exception e){
                throw new SQLException(e);
            }

            return flowerbeds;

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem selecting all the flowerbeds", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
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
