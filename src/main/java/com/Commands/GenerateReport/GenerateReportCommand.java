package com.Commands.GenerateReport;

import com.Commands.Command;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.utils.FileUtils.FileUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Command which generates a simple aggregated report from the database data
 */
public class GenerateReportCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();
    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    @Override
    public void execute() {
        try{
            ArrayList<Planting> plantings = plantingDao.findAll();
            int numberOfPlantings = plantings.size();
            String currentDirectory = System.getProperty("user.dir");
            String reportFileAbsolutePath = currentDirectory + "/GardenReport.txt";

            if((new File(reportFileAbsolutePath).exists())) FileUtils.clearFile(reportFileAbsolutePath);

            String numberOfPlantingsReport = "Number of Plantings" + "\n" +
                                            "===================" + "\n" +
                                            numberOfPlantings + "\n";

            String totalNumberOfStoredSeedsByPlantReportHeader =  "Number of Stored Seeds per Plant" + "\n" +
                                                                  "================================" + "\n";

            FileUtils.appendToFile(numberOfPlantingsReport, reportFileAbsolutePath);

            ArrayList<ArrayList<String>> totalNumberOfStoredSeedsByPlant = storageRecordDao.findNumberOfStoredSeedsPerPlant();

            FileUtils.appendToFile(totalNumberOfStoredSeedsByPlantReportHeader, reportFileAbsolutePath);

            for(ArrayList<String> record : totalNumberOfStoredSeedsByPlant){
                String formattedRecord = String.format("%-12s%s", record.get(0), record.get(1)) + "\n";
                FileUtils.appendToFile(formattedRecord, reportFileAbsolutePath);
            }

            System.out.println("Report successfully generated in " + currentDirectory);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
