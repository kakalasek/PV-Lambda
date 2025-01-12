package com.DbObjects.Storage.StorageRecord;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;

public class StorageRecord {

    private int id;
    private Packaging packaging;
    private Plant plant;

    public StorageRecord(int id, Packaging packaging, Plant plant){
        this.id = id;
        this.packaging = packaging;
        this.plant = plant;
    }

    public StorageRecord(Packaging packaging, Plant plant){
        this.id = 0;
        this.packaging = packaging;
        this.plant = plant;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public Plant getPlant() {
        return plant;
    }

    public int getId() {
        return id;
    }

}
