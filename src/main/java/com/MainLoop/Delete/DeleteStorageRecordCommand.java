package com.MainLoop.Delete;

import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;
import com.MainLoop.Command;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;

import java.util.ArrayList;
import java.util.Scanner;

public class DeleteStorageRecordCommand implements Command {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();

    @Override
    public void execute() {
        try{
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<StorageRecord> storageRecords = storageRecordDao.findAll();
            for(int i = 0; i < storageRecords.size(); i++) {
                StorageRecord storageRecord = storageRecords.get(i);
                System.out.println(i + " | " + storageRecord.getPlant().getName() + ", " + storageRecord.getPackaging().getExpirationDate() + ", " + storageRecord.getPackaging().getNumberOfSeeds());
            }

            System.out.println("Choose storage record by its index");
            String storageRecordPickString = sc.nextLine();
            if(!InputChecker.isPositiveNumber(storageRecordPickString)){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }
            int storageRecordPick = Integer.parseInt(storageRecordPickString);
            if(storageRecordPick > storageRecords.size()){
                System.out.println("Your pick must be a number within the respective range");
                return;
            }

            storageRecordDao.delete(storageRecords.get(storageRecordPick).getId());

            System.out.println("Storage record deleted successfully");
        } catch (Exception e){
            System.out.println("There has been an unexpected error");
        }
    }
}
