package com.DbObjects.Storage;

import com.DbObjects.Plant.Plant;

public class StorageRecord {

    private Packaging packaging;
    private Plant plant;

    public StorageRecord(Packaging packaging, Plant plant){
        this.packaging = packaging;
        this.plant = plant;
    }
}
