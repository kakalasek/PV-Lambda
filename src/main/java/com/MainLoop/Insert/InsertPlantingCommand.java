package com.MainLoop.Insert;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Planting.Flowerbed.Flowerbed;
import com.DbObjects.Planting.Flowerbed.FlowerbedDaoImpl;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.MainLoop.Command;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class InsertPlantingCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();
    FlowerbedDaoImpl flowerbedDao = new FlowerbedDaoImpl();
    PlantingDaoImpl plantingDao = new PlantingDaoImpl();
    PlantDaoImpl plantDao = new PlantDaoImpl();

    @Override
    public void execute() {
        try{
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();
            for(int i = 0; i < storageRecords.size(); i++){
                StorageRecord storageRecord = storageRecords.get(i);
                System.out.println(i + " | " + storageRecord.getPlant().getName() + ", " + storageRecord.getPackaging().getExpirationDate() + ", " + storageRecord.getPackaging().getNumberOfSeeds());
            }


            System.out.println("Choose seeds by their index");
            String seedsPickString = sc.nextLine();
            if(!InputChecker.isPositiveNumber(seedsPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }
            int seedsPick = Integer.parseInt(seedsPickString);
            if(seedsPick > storageRecords.size()){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            StorageRecord storageRecord = storageRecords.get(seedsPick);

            // TODO create a way to get id of packaging

            storageRecord.getPlant().setId(plantDao.findByName(storageRecord.getPlant().getName()).getId());

            Date dateFrom;
            System.out.println("Enter date of planting in this format: YYYY-MM-DD");
            String dateString = sc.nextLine();
            try {
                dateFrom = Date.valueOf(dateString);
            } catch (IllegalArgumentException e){
                System.out.println("You have entered the date of planting in an invalid format");
                return;
            }

            System.out.println("Enter the number of seeds you want to plant");
            String numberOfSeedsString = sc.nextLine();
            if(!InputChecker.isPositiveNumber(numberOfSeedsString)) {
                System.out.println("The number of seeds has to be a number");
                return;
            }
            int numberOfSeeds = Integer.parseInt(numberOfSeedsString);
            if(numberOfSeeds > storageRecord.getPackaging().getNumberOfSeeds()) {
                System.out.println("The number of seeds must be equal to or less than the number of seeds inside the chosen package");
                return;
            }

            storageRecordDao.updateNumberOfSeeds(storageRecord, storageRecord.getPackaging().getNumberOfSeeds() - numberOfSeeds);

            ArrayList<Flowerbed> flowerbeds = flowerbedDao.findAll();
            for(Flowerbed flowerbed : flowerbeds){
                System.out.println(flowerbed.getNumber() + " | " + flowerbed.getSize() + "m^2");
            }

            System.out.println("Choose a flowerbed by its number");
            String flowerbedPickString = sc.nextLine();
            if(!InputChecker.isPositiveNumber(flowerbedPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            Flowerbed flowerbed = null;
            int flowerbedPick = Integer.parseInt(flowerbedPickString);
            for(Flowerbed flowerbedInsideLoop : flowerbeds){
                if (flowerbedInsideLoop.getNumber() == flowerbedPick){
                    flowerbed = flowerbedInsideLoop;
                }
            }
            if(flowerbed == null){
                System.out.println("Your pick must be a number of one of the flowerbeds");
                return;
            }

            Planting planting = new Planting(dateFrom, null, numberOfSeeds, flowerbed, storageRecord.getPlant());

            plantingDao.insert(planting);

            System.out.println("Planting inserted successfully");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
