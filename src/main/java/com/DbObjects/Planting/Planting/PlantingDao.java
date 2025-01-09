package com.DbObjects.Planting.Planting;

import com.DbObjects.Dao;

import java.util.ArrayList;
import java.sql.Date;

public interface PlantingDao extends Dao<Planting> {
    ArrayList<Planting> findAll();
    ArrayList<Planting> findAllPlanted();
    void liquidatePlanting(int id, Date liquidationDate);
}
