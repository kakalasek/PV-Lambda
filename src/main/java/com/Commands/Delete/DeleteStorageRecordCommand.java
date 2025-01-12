package com.Commands.Delete;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;
import com.utils.HandyTools.HandyTools;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

/**
 * Command which lets the user delete a storage record
 */
public class DeleteStorageRecordCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

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

    @Override
    public void execute() {
        try{
            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();

            String renderedTable = generateStorageRecordTable(storageRecords);

            System.out.println(renderedTable);

            int storageRecordPick = HandyTools.chooseFromList("Choose storage record by its index", storageRecords.size());

            storageRecordDao.delete(storageRecords.get(storageRecordPick).getId());

            System.out.println("Storage record deleted successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
