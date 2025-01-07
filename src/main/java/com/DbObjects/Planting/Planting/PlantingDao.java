package com.DbObjects.Planting.Planting;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlantingDao extends Dao<Planting> {
    ArrayList<Planting> findAll() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException;
}
