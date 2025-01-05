package com.MainLoop.Insert;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Planting.Flowerbed;
import com.DbObjects.Planting.FlowerbedDaoImpl;
import com.DbObjects.Planting.Planting;
import com.DbObjects.Planting.PlantingDaoImpl;
import com.DbObjects.Storage.Packaging;
import com.DbObjects.Storage.StorageRecord;
import com.DbObjects.Storage.StorageRecordDaoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Inserter {

    private final Scanner sc;
    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();
    PlantingDaoImpl plantingDao = new PlantingDaoImpl();
    PlantDaoImpl plantDao = new PlantDaoImpl();
    FlowerbedDaoImpl flowerbedDao = new FlowerbedDaoImpl();

    public Inserter(Scanner sc){
        this.sc = sc;
    }

    public Date inputDate(String prompt) throws IllegalArgumentException{
        System.out.println(prompt);
        String DateString = sc.nextLine();
        return Date.valueOf(DateString);
    }

    public String insertStorageRecord() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<Plant> plants = plantDao.getAll();
        for(int i = 0; i < plants.size(); i++){
            System.out.println(i + " | " + plants.get(i).getName());
        }

        System.out.println("Choose a plant by its index");
        String plantPickString = sc.nextLine();
        if(!plantPickString.matches("\\d+")) return "Your pick must be a number within the respective range";

        int plantPick = Integer.parseInt(plantPickString);
        if(plantPick > plants.size()) return "Your pick must be a number within the respective range";

        Plant plant = plants.get(plantPick);

        Date expirationDate;
        try {
            expirationDate = inputDate("Enter date of package expiration in this format: YYYY-MM-DD");
        } catch (IllegalArgumentException e){
            return "You have entered the expiration date in an invalid format";
        }

        System.out.println("Enter the number of seeds inside the package");
        String numberOfSeedsString = sc.nextLine();
        if(!numberOfSeedsString.matches("\\d+")) return "The number of seeds has to be a number";
        int numberOfSeeds = Integer.parseInt(numberOfSeedsString);

        Packaging packaging = new Packaging(expirationDate, numberOfSeeds);

        StorageRecord storageRecord = new StorageRecord(packaging, plant);

        storageRecordDao.insert(storageRecord);

        return "Storage record inserted successfully";
    }

    public String insertPlanting() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<StorageRecord> storageRecords = storageRecordDao.getAll();
        for(int i = 0; i < storageRecords.size(); i++){
            StorageRecord storageRecord = storageRecords.get(i);
            System.out.println(i + " | " + storageRecord.getPlant().getName() + ", " + storageRecord.getPackaging().getExpirationDate() + ", " + storageRecord.getPackaging().getNumberOfSeeds());
        }


        System.out.println("Choose seeds by their index");
        String seedsPickString = sc.nextLine();
        if(!seedsPickString.matches("\\d+")) return "Your pick must be a number within the respective range";

        int seedsPick = Integer.parseInt(seedsPickString);
        if(seedsPick > storageRecords.size()) return "Your pick must be a number within the respective range";

        StorageRecord storageRecord = storageRecords.get(seedsPick);

        Date dateFrom;
        try {
            dateFrom = inputDate("Enter date of planting in this format: YYYY-MM-DD");
        } catch (IllegalArgumentException e){
            return "You have entered the date of planting in an invalid format";
        }

        System.out.println("Enter the number of seeds you want to plant");
        String numberOfSeedsString = sc.nextLine();
        if(!numberOfSeedsString.matches("\\d+")) return "The number of seeds has to be a number";
        int numberOfSeeds = Integer.parseInt(numberOfSeedsString);
        if(numberOfSeeds > storageRecord.getPackaging().getNumberOfSeeds()) return "The number of seeds must be equal to or less than the number of seeds inside the chosen package";

        storageRecord.getPackaging().setNumberOfSeeds(numberOfSeeds);
        storageRecordDao.updateNumberOfSeeds(storageRecord);

        ArrayList<Flowerbed> flowerbeds = flowerbedDao.getAll();
        for(Flowerbed flowerbed : flowerbeds){
            System.out.println(flowerbed.getNumber() + " | " + flowerbed.getSize() + "m^2");
        }

        System.out.println("Choose a flowerbed by its number");
        String flowerbedPickString = sc.nextLine();
        if(!flowerbedPickString.matches("\\d+")) return "Your pick must be a number within the respective range";

        Flowerbed flowerbed = null;
        int flowerbedPick = Integer.parseInt(flowerbedPickString);
        for(Flowerbed flowerbedInsideLoop : flowerbeds){
            if (flowerbedInsideLoop.getNumber() == flowerbedPick){
                flowerbed = flowerbedInsideLoop;
            }
        }
        if(flowerbed == null) return "Your pick must be a number of one of the flowerbeds";

        Planting planting = new Planting(dateFrom, null, numberOfSeeds, flowerbed, storageRecord.getPlant());

        plantingDao.insert(planting);

        return "Planting inserted successfully";
    }
}
