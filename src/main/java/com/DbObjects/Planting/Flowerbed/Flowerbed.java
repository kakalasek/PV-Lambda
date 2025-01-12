package com.DbObjects.Planting.Flowerbed;

public class Flowerbed {

    private int id;
    private double size;
    private int number;

    public Flowerbed(int id, double size, int number){
        this.id = id;
        this.size = size;
        this.number = number;
    }

    public Flowerbed(double size, int number){
        this.id = 0;
        this.size = size;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public double getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

}
