package com.DbObjects.Planting.Planting;

import com.DbObjects.Dao;

import java.util.ArrayList;

public interface PlantingDao extends Dao<Planting> {
    ArrayList<Planting> findAll();
    ArrayList<Planting> findAllPlanted();
}
