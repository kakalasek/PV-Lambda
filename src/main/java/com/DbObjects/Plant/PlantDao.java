package com.DbObjects.Plant;

import com.DbObjects.Dao;

import java.util.ArrayList;

public interface PlantDao extends Dao<Plant> {
    ArrayList<Plant> findAll();
    Plant findByName(String name);
}
