package com.Commands.Select;

import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.Commands.Command;

import java.util.ArrayList;

public class SelectStorageRecordsCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    @Override
    public void execute() {
        try {
            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();
            StringBuilder output = new StringBuilder("Expiration Date | Number Of Seeds | Plant Name | Life Length | Growing Time | Spacing | Planting Depth | Planting Time | Pre Growing \n");
            for (StorageRecord storageRecord : storageRecords) {
                output.append(storageRecord.toString()).append("\n");
            }
            System.out.println(output.toString());
        } catch (Exception e){

        }
    }
}
