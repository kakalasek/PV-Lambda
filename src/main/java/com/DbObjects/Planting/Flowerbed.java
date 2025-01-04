package com.DbObjects.Planting;

public class Flowerbed {

    private double size;
    private int number;

    public Flowerbed(double size, int number){
        this.size = size;
        this.number = number;
    }

    public String toString(){
        return size + ", " + number;
    }
}
