package com.DbObjects.Plant;

public class Plant {

    public enum plantType{
        Annual,
        Perennial,
        Biennial
    }

    private String name;
    private plantType lifeLength;
    private int growingTime;
    private int spacing;
    private int plantingDepth;
    private int plantingTime;
    private boolean pre_growing;

    public Plant(String name, plantType lifeLength, int growingTime, int spacing, int plantingDepth, int plantingTime, boolean pre_growing) {
        this.name = name;
        this.lifeLength = lifeLength;
        this.growingTime = growingTime;
        this.spacing = spacing;
        this.plantingTime = plantingTime;
        this.pre_growing = pre_growing;
    }
}
