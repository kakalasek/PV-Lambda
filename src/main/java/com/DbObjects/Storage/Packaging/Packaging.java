package com.DbObjects.Storage.Packaging;

import java.sql.Date;

public class Packaging {

    private int id;
    private Date expirationDate;
    private int numberOfSeeds;

    public Packaging(int id, Date expirationDate, int numberOfSeeds){
        this.id = id;
        this.expirationDate = expirationDate;
        this.numberOfSeeds = numberOfSeeds;
    }

    public Packaging(Date expirationDate, int numberOfSeeds){
        this.id = 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String toString(){
        return (expirationDate != null ? expirationDate : "Unknown") + ", " +
                (numberOfSeeds != 0 ? numberOfSeeds : "Unknown");
    }
}
