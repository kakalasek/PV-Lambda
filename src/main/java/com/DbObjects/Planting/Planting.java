package com.DbObjects.Planting;

import com.DbObjects.Plant.Plant;

import java.sql.Date;

public class Planting {

    private Date dateFrom;
    private Date dateTo;
    private int numberOfSeeds;
    private Flowerbed flowerbed;
    private Plant plant;

    public Planting(Date dateFrom, Date dateTo, int numberOfSeeds, Flowerbed flowerbed, Plant plant){
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numberOfSeeds = numberOfSeeds;
        this.flowerbed = flowerbed;
        this.plant = plant;
    }

    public String toString(){
        return dateFrom + ", " + dateTo + ", " + numberOfSeeds + ", " + flowerbed.toString() + ", " + plant.toString();
    }
}
