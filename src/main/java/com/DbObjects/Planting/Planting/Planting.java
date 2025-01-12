package com.DbObjects.Planting.Planting;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Flowerbed.Flowerbed;

import java.sql.Date;

public class Planting {

    private int id;
    private Date dateFrom;
    private Date dateTo;
    private int numberOfSeeds;
    private Flowerbed flowerbed;
    private Plant plant;

    public Planting(int id, Date dateFrom, Date dateTo, int numberOfSeeds, Flowerbed flowerbed, Plant plant){
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numberOfSeeds = numberOfSeeds;
        this.flowerbed = flowerbed;
        this.plant = plant;
    }

    public Planting(Date dateFrom, Date dateTo, int numberOfSeeds, Flowerbed flowerbed, Plant plant){
        this.id = 0;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numberOfSeeds = numberOfSeeds;
        this.flowerbed = flowerbed;
        this.plant = plant;
    }

    public int getId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public int getNumberOfSeeds() {
        return numberOfSeeds;
    }

    public Flowerbed getFlowerbed() {
        return flowerbed;
    }

    public Plant getPlant() {
        return plant;
    }

}
