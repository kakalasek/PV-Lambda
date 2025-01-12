package com.Commands.Select;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

/**
 * Command which displays all the storage records inside the database
 */
public class SelectStorageRecordsCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    /**
     * Will generate a simple table of storage records
     * @param storageRecords A list of storage records
     * @return The rendered table string
     */
    private String generateStorageRecordTable(ArrayList<StorageRecord> storageRecords){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Expiration Date", "Number Of Seeds", "Plant Name", "Life Length", "Growing Time (day)", "Spacing cm", "Planting Depth cm", "Planting Time (month)", "Pre Growing");
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
