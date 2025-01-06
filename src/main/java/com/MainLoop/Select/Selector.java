package com.MainLoop.Select;

import com.CustomExceptions.CouldNotEstablishConnectionException;
import com.CustomExceptions.LoadingPropertiesException;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import com.DbObjects.Storage.StorageRecord.StorageRecordDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class Selector {

    StorageRecordDaoImpl storageRecordDao = new StorageRecordDaoImpl();
    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    public String selectStorageRecords() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<StorageRecord> storageRecords = storageRecordDao.getAll();
        StringBuilder output = new StringBuilder("Expiration Date | Number Of Seeds | Plant Name | Life Length | Growing Time | Spacing | Planting Depth | Planting Time | Pre Growing \n");
        for (StorageRecord storageRecord : storageRecords) {
            output.append(storageRecord.toString()).append("\n");
        }
        return output.toString();
    }

    public String selectPlantings() throws LoadingPropertiesException, SQLException, CouldNotEstablishConnectionException {
        ArrayList<Planting> plantings = plantingDao.getAll();
        StringBuilder output = new StringBuilder("Date Of Planting | Date Of Liquidation | Number Of Seeds | Flowerbed Size | Flowerbed Number | Plant Name | Life Length | Growing Time | Spacing | Planting Depth | Planting Time | Pre Growing \n");
        for (Planting planting : plantings){
            output.append(planting.toString()).append("\n");
        }
        return output.toString();
    }
}
