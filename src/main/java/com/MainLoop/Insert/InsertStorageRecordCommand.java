package com.MainLoop.Insert;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.MainLoop.Command;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class InsertStorageRecordCommand implements Command {

    private final PlantDaoImpl plantDao = new PlantDaoImpl();
    private final StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    @Override
    public void execute() {
        try {
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<Plant> plants = plantDao.findAll();
            for (int i = 0; i < plants.size(); i++) {
                System.out.println(i + " | " + plants.get(i).getName());
            }

            System.out.println("Choose a plant by its index");
            String plantPickString = sc.nextLine();
            if (!InputChecker.isPositiveNumber(plantPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            int plantPick = Integer.parseInt(plantPickString);
            if (plantPick > plants.size()) {
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            Plant plant = plants.get(plantPick);

            Date expirationDate;
            System.out.println("Enter date of package expiration in this format: YYYY-MM-DD");
            String dateString = sc.nextLine();
            try {
                expirationDate = Date.valueOf(dateString);
            } catch (IllegalArgumentException e) {
                System.out.println("You have entered the expiration date in an invalid format");
                return;
            }

            System.out.println("Enter the number of seeds inside the package");
            String numberOfSeedsString = sc.nextLine();
            if (!InputChecker.isPositiveNumber(numberOfSeedsString)){
                System.out.println("The number of seeds has to be a number");
                return;
            }
            int numberOfSeeds = Integer.parseInt(numberOfSeedsString);

            Packaging packaging = new Packaging(expirationDate, numberOfSeeds);

            StorageRecord storageRecord = new StorageRecord(packaging, plant);

            storageRecordDao.insert(storageRecord);

            System.out.println("Storage record inserted successfully");
        } catch (Exception e){
            System.out.println("There has been an unexpected problem");
        }
    }
}
