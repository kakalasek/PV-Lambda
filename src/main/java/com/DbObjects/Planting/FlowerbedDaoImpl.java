package com.DbObjects.Planting;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.Database.DatabaseConnection;
import com.DbObjects.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowerbedDaoImpl implements Dao<Flowerbed> {
    @Override
    public ArrayList<Flowerbed> getAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<Flowerbed> flowerbeds = new ArrayList<>();
        DatabaseConnection conn = new DatabaseConnection();
        conn.close();

        String sqlSelectAll = "SELECT * FROM Flowerbed;";

        try(PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)){
            ResultSet rs = psSelectAll.executeQuery();

            while(rs.next()){
                double size = rs.getDouble("size");
                int number = rs.getInt("number");
                Flowerbed flowerbed = new Flowerbed(size, number);

                flowerbeds.add(flowerbed);
            }
        }

        conn.close();

        return flowerbeds;
    }

    @Override
    public void insert(Flowerbed item) throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {

    }
}
