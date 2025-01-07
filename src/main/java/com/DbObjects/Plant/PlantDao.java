package com.DbObjects.Plant;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlantDao extends Dao<Plant> {
    ArrayList<Plant> findAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException;
}
