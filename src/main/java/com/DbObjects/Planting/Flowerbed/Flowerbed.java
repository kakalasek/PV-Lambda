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

    public double getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }

    public String toString(){
        return (size != 0.0 ? size + "m^2" : "Unknown") + ", " +
                number;
    }
}
