package com.DbObjects.Plant;

public class Plant {

    public enum plantType{
        ANNUAL,
        PERENNIAL,
        BIENNIAL
    }

    private String name;
    private plantType lifeLength;
    private int growingTime;
    private int spacing;
    private int plantingTime;
    private boolean pre_growing;

}
