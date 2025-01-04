package com.DbObjects.Storage;

import java.sql.Date;

public class Packaging {

    private Date expirationDate;
    private int numberOfSeeds;

    public Packaging(Date expirationDate, int numberOfSeeds){
        this.expirationDate = expirationDate;
        this.numberOfSeeds = numberOfSeeds;
    }

    public String toString(){
        return expirationDate + ", " + numberOfSeeds;
    }
}
