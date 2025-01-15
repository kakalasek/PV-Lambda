package com.Commands.ImportFromFile;

import com.Commands.Command;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Plant.PlantDaoImpl;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.utils.FileUtils.FileUtils;
import com.utils.HandyTools.HandyTools;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Command which allows the user to import storage records from csv
 */
public class ImportStorageRecordsFromCsvCommand implements Command {

    PlantDaoImpl plantDao = new PlantDaoImpl();
    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    @Override
    public void execute() {
        try {

            String filePath = HandyTools.chooseFile("Enter an absolute filepath of your csv file");

            List<List<String>> csvValues = FileUtils.readCsvFile(filePath);

            ArrayList<StorageRecord> storageRecords = new ArrayList<>();

            for(int i = 1; i < csvValues.size(); i++){
                List<String> currentRecord = csvValues.get(i);
                Plant plant = plantDao.findByName(currentRecord.get(0));
                Packaging packaging = new Packaging(Date.valueOf(currentRecord.get(2)), Integer.parseInt(currentRecord.get(1)));
                StorageRecord storageRecord = new StorageRecord(packaging, plant);
                storageRecords.add(storageRecord);
            }

            storageRecordDao.bulkInsert(storageRecords);

            System.out.println("Inserting from the csv file completed successfully");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
