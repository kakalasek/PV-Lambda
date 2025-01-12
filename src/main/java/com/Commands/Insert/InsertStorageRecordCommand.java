package com.Commands.Insert;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;
import com.utils.HandyTools.HandyTools;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;
import de.vandermeer.asciitable.AsciiTable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Commands which lets the user insert a storage record into the database
 */
public class InsertStorageRecordCommand implements Command {

    private final PlantDaoImpl plantDao = new PlantDaoImpl();
    private final StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    /**
     * Will generate a simple table of plants and their choice indexes
     * @param plants A list of plants
     * @return The rendered table string
     */
    private String generatePlantTable(ArrayList<Plant> plants){
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Choice", "Plant Name");
        table.addRule();

        for (int i = 0; i < plants.size(); i++){
            Plant currentPlant = plants.get(i);
            table.addRow(i, currentPlant.getName());
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try {
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<Plant> plants = plantDao.findAll();

            String renderTable = generatePlantTable(plants);

            System.out.println(renderTable);

            int plantPick = HandyTools.chooseFromList("Choose a plant by its index", plants.size());

            Plant plant = plants.get(plantPick);

            Date expirationDate = HandyTools.pickDate("expirationDate");

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
            System.out.println(e.getMessage());
        }
    }
}
