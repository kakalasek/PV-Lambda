package com.Commands.Insert;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Planting.Flowerbed.Flowerbed;
import com.DbObjects.Planting.Flowerbed.FlowerbedDaoImpl;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.Packaging.PackagingDaoImpl;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;
import com.utils.HandyTools.HandyTools;
import de.vandermeer.asciitable.AsciiTable;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Command which lets the user insert a planting
 */
public class InsertPlantingCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();
    FlowerbedDaoImpl flowerbedDao = new FlowerbedDaoImpl();
    PlantingDaoImpl plantingDao = new PlantingDaoImpl();
    PlantDaoImpl plantDao = new PlantDaoImpl();
    PackagingDaoImpl packagingDao = new PackagingDaoImpl();

    /**
     * Will generate a simple table of storage records and their choice indexes
     * @param storageRecords A list of storage records
     * @return The rendered table string
     */
    private String generateStorageRecordTable(ArrayList<StorageRecord> storageRecords){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Choice", "Plant Name", "Expiration Date", "Number Of Seeds");
        table.addRule();

        for (int i = 0; i < storageRecords.size(); i++){
            Packaging packaging = storageRecords.get(i).getPackaging();
            Plant plant = storageRecords.get(i).getPlant();
            table.addRow(i, plant.getName(), packaging.getExpirationDate(), packaging.getNumberOfSeeds());
        }

        table.addRule();

        return table.render();
    }

    /**
     * Will generate a simple table of flowerbeds and their choice indexes
     * @param flowerbeds A list of flowerbeds
     * @return The rendered table string
     */
    private String generateFlowerbedTable(ArrayList<Flowerbed> flowerbeds){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Choice", "Number", "Size m^2");
        table.addRule();

        for (int i = 0; i < flowerbeds.size(); i++){
            Flowerbed currentFlowerbed = flowerbeds.get(i);
            table.addRow(i, currentFlowerbed.getNumber(), currentFlowerbed.getSize());
        }

        table.addRule();

        return table.render();
    }


    @Override
    public void execute() {
        try{
            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();

            String renderedTable = generateStorageRecordTable(storageRecords);

            System.out.println(renderedTable);

            int storageRecordPick = HandyTools.chooseFromList("Choose seeds by their index", storageRecords.size());

            StorageRecord storageRecord = storageRecords.get(storageRecordPick);
            int storageRecordId = storageRecord.getId();

            Packaging packaging = storageRecord.getPackaging();
            int packagingId = packagingDao.findByStorageRecordId(storageRecordId).getId();
            packaging.setId(packagingId);

            Plant plant = storageRecord.getPlant();
            int plantId = plantDao.findByName(plant.getName()).getId();
            plant.setId(plantId);

            Date dateFrom = HandyTools.pickDate("planting date");

            int numberOfSeedsToPlant = HandyTools.choosePositiveNumberWithLimit("Enter the number of seeds you want to plant",
                    "The number of seeds has to be a number",
                    "The number of seeds must be equal to or less than the number of seeds inside the chosen package",
                    packaging.getNumberOfSeeds());

            storageRecordDao.updateNumberOfSeeds(storageRecord, packaging.getNumberOfSeeds() - numberOfSeedsToPlant);

            ArrayList<Flowerbed> flowerbeds = flowerbedDao.findAll();

            renderedTable = generateFlowerbedTable(flowerbeds);

            System.out.println(renderedTable);

            int chosenFlowerbed = HandyTools.chooseFromList("Choose a flowerbed by its index", flowerbeds.size());

            Flowerbed flowerbed = flowerbeds.get(chosenFlowerbed);

            Planting planting = new Planting(dateFrom, null, numberOfSeedsToPlant, flowerbed, storageRecord.getPlant());

            plantingDao.insert(planting);

            System.out.println("Planting inserted successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
