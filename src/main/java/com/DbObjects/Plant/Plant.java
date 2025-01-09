package com.DbObjects.Plant;

public class Plant {

    public enum plantType{
        Unknown,
        Annual,
        Perennial,
        Biennial
    }

    private int id;
    private String name;
    private plantType lifeLength;
    private int growingTime;
    private int spacing;
    private int plantingDepth;
    private int plantingTime;
    private boolean pre_growing;

    public Plant(int id, String name, plantType lifeLength, int growingTime, int spacing, int plantingDepth, int plantingTime, boolean pre_growing) {
        this.id = id;
        this.name = name;
        this.lifeLength = lifeLength;
        this.growingTime = growingTime;
        this.spacing = spacing;
        this.plantingDepth = plantingDepth;
        this.plantingTime = plantingTime;
        this.pre_growing = pre_growing;
    }

    public Plant(String name, plantType lifeLength, int growingTime, int spacing, int plantingDepth, int plantingTime, boolean pre_growing) {
        this.id = 0;
        this.name = name;
        this.lifeLength = lifeLength;
        this.growingTime = growingTime;
        this.spacing = spacing;
        this.plantingDepth = plantingDepth;
        this.plantingTime = plantingTime;
        this.pre_growing = pre_growing;
    }

    public String getName(){
        return this.name;
    }

    public plantType getLifeLength() {
        return lifeLength;
    }

    public int getGrowingTime() {
        return growingTime;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getPlantingDepth() {
        return plantingDepth;
    }

    public int getPlantingTime() {
        return plantingTime;
    }

    public boolean isPre_growing() {
        return pre_growing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return name + ", " +
                lifeLength + ", " +
                (growingTime != 0 ? growingTime + "days" : "Unknown") + ", " +
                (spacing != 0 ? spacing + "cm" : "Unknown") + ", " +
                (plantingDepth != 0 ?  plantingDepth + "cm" : "Unknown") + ", " +
                (plantingTime != 0 ? plantingTime + "(month)" : "Unknown") + ", " +
                (pre_growing ? "yes" : "no");
    }
}
