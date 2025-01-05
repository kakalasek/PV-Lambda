package com.DbObjects.Storage;

import java.sql.Date;

public class Packaging {

    private Date expirationDate;
    private int numberOfSeeds;

    public Packaging(Date expirationDate, int numberOfSeeds){
        this.expirationDate = expirationDate;
        this.numberOfSeeds = numberOfSeeds;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getNumberOfSeeds() {
        return numberOfSeeds;
    }

    public void setNumberOfSeeds(int numberOfSeeds){
        this.numberOfSeeds = numberOfSeeds;
    }

    public String toString(){
        return (expirationDate != null ? expirationDate : "Unknown") + ", " +
                (numberOfSeeds != 0 ? numberOfSeeds : "Unknown");
    }
}
