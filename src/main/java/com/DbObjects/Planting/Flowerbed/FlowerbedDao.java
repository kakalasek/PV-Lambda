package com.DbObjects.Planting.Flowerbed;

import com.DbObjects.Dao;

import java.util.ArrayList;

public interface FlowerbedDao extends Dao<Flowerbed> {
    ArrayList<Flowerbed> findAll();
}
