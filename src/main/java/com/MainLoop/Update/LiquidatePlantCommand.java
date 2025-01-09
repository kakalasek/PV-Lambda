package com.MainLoop.Update;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.MainLoop.Command;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class LiquidatePlantCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    @Override
    public void execute() {
        try{
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<Planting> plantings = plantingDao.findAllPlanted();
            System.out.println("Date Of Planting | Number Of Seeds | Flowerbed Number | Plant Name \n");
            for (int i = 0; i < plantings.size(); i++){
                System.out.println(i + " | " + plantings.get(i).getDateFrom() + ", " + plantings.get(i).getNumberOfSeeds() + ", " + plantings.get(i).getFlowerbed().getNumber() + ", " + plantings.get(i).getPlant().getName());
            }

            System.out.println("Choose a planting by its index");
            String plantingPickString = sc.nextLine();
            if (!InputChecker.isPositiveNumber(plantingPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            int plantingPick = Integer.parseInt(plantingPickString);
            if (plantingPick > plantings.size()) {
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            Planting planting = plantings.get(plantingPick);

            Date liquidationDate;
            System.out.println("Enter date of plant liquidation in this format: YYYY-MM-DD");
            String dateString = sc.nextLine();
            try {
                liquidationDate = Date.valueOf(dateString);
            } catch (IllegalArgumentException e) {
                System.out.println("You have entered the liquidation date in an invalid format");
                return;
            }

            planting.setDateTo(liquidationDate);

            plantingDao.update(planting);

            System.out.println("Plant liquidated successfully");
        } catch (Exception e){
            System.out.println("There has been an unexpected error");
        }
    }
}
