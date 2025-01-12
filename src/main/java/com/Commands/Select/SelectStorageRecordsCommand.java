package com.Commands.Select;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

public class SelectStorageRecordsCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    private String generateStorageRecordTable(ArrayList<StorageRecord> storageRecords){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Expiration Date", "Number Of Seeds", "Plant Name", "Life Length", "Growing Time", "Spacing", "Planting Depth", "Planting Time", "Pre Growing");
        table.addRule();

        for (StorageRecord storageRecord : storageRecords){
            Packaging packaging = storageRecord.getPackaging();
            Plant plant = storageRecord.getPlant();
            table.addRow(packaging.getExpirationDate(), packaging.getNumberOfSeeds(), plant.getName(), plant.getLifeLength(), plant.getGrowingTime(), plant.getSpacing(), plant.getPlantingDepth(), plant.getPlantingTime(), plant.needsPreGrowing());
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try {
            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();
            String renderedTable = generateStorageRecordTable(storageRecords);
            System.out.println(renderedTable);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
