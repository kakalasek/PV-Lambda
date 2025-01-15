package com.DbObjects.Storage.StorageRecord;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;
import com.DbObjects.Plant.Plant;
import com.DbObjects.Storage.Packaging.Packaging;

import java.sql.*;
import java.util.ArrayList;

public class StorageRecordDaoImpl implements StorageRecordDao {

    /**
     * Retrieves all storage records from the database.
     * NOTE that this method uses a view in order to return all the storage records. This view does not contain
     * ids of Packaging or Plant, so they will be set to 0 by default
     * @return The ArrayList of storage records
     */
    @Override
    public ArrayList<StorageRecord> findAll() {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            ArrayList<StorageRecord> storageRecords = new ArrayList<>();
            conn.connect();

            String sqlSelectAll = "SELECT * FROM Stored_plants;";

            try (PreparedStatement psSelectAll = conn.getConnection().prepareStatement(sqlSelectAll)) {
                ResultSet rs = psSelectAll.executeQuery();

                while (rs.next()) {
                    String plantName = rs.getString("Plant_Name");
                    Plant.plantType plantLifeLength = Plant.plantType.valueOf(rs.getString("Life_Length"));
                    int plantGrowingTime = rs.getInt("Time_To_Grow");
                    int plantSpacing = rs.getInt("Spacing");
                    int plantPlantingDepth = rs.getInt("Depth_Of_Planting");
                    int plantPlantingTime = rs.getInt("Month_To_Plant");
                    boolean plantPreGrowing = rs.getBoolean("Pre_Growing");
                    Plant plant = new Plant(plantName,
                            plantLifeLength,
                            plantGrowingTime,
                            plantSpacing,
                            plantPlantingDepth,
                            plantPlantingTime,
                            plantPreGrowing);

                    Date packagingExpirationDate = rs.getDate("Expiration_Date");
                    int packagingNumberOfSeeds = rs.getInt("Number_Of_Seeds_In_Package");
                    Packaging packaging = new Packaging(packagingExpirationDate, packagingNumberOfSeeds);

                    int id = rs.getInt("Stored_Plant_id");

                    StorageRecord storageRecord = new StorageRecord(id, packaging, plant);
                    storageRecords.add(storageRecord);
                }
            } catch (Exception e){
                throw new SQLException(e);
            }


            return storageRecords;
        } catch (SQLException e) {
            throw new RuntimeException("There has been a problem returning all the storage records");
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    /**
     * Selects the total number of stored seeds per plant
     * @return A List of Lists containing two values, number of seeds and plant name, respectively
     */
    @Override
    public ArrayList<ArrayList<String>> findNumberOfStoredSeedsPerPlant() {
        DatabaseConnection conn = new DatabaseConnection();
        try{
            ArrayList<ArrayList<String>> totalNumberOfSeedsPerPlant = new ArrayList<>();
            conn.connect();

            String sqlSelectTotalNumberOfSeedsPerPlant = "SELECT SUM(Packaging.number_of_seeds) AS Total_Number_Of_Seeds, Plant.name AS Plant_Name FROM Storage INNER JOIN Packaging ON Storage.packaging_id = Packaging.id INNER JOIN Plant ON Storage.plant_id = Plant.id GROUP BY Plant.name";

            try (PreparedStatement psSelectTotalNumberOfSeedsPerPlant = conn.getConnection().prepareStatement(sqlSelectTotalNumberOfSeedsPerPlant)) {
                ResultSet rs = psSelectTotalNumberOfSeedsPerPlant.executeQuery();

                while (rs.next()) {
                    int totalNumberOfSeeds = rs.getInt("Total_Number_Of_Seeds");
                    String plantName = rs.getString("Plant_Name");

                    ArrayList<String> record = new ArrayList<>();
                    record.add(String.valueOf(totalNumberOfSeeds));
                    record.add(plantName);

                    totalNumberOfSeedsPerPlant.add(record);
                }
            } catch (Exception e){
                throw new SQLException(e);
            }

            return totalNumberOfSeedsPerPlant;

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem selecting the number of stored seeds per plant", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    /**
     * Will insert multiple storage records at once. Note that it will wrap them inside a transaction, so if anything fails, they will be rolled back
     * @param storageRecords The ArrayList of Storage Records you want to insert
     */
    @Override
    public void bulkInsert(ArrayList<StorageRecord> storageRecords) {
        DatabaseConnection conn = new DatabaseConnection();
        try{
            conn.connect();
            conn.getConnection().setAutoCommit(false);

            for(StorageRecord storageRecord : storageRecords) {
                String sqlInsertPackaging = "INSERT INTO Packaging(expiration_date, number_of_seeds) " +
                        "VALUES (?, ?);";
                int packagingId = 0;
                Packaging packaging = storageRecord.getPackaging();

                try (PreparedStatement psInsertPackaging = conn.getConnection().prepareStatement(sqlInsertPackaging, Statement.RETURN_GENERATED_KEYS)) {
                    psInsertPackaging.setDate(1, packaging.getExpirationDate());
                    psInsertPackaging.setInt(2, packaging.getNumberOfSeeds());

                    psInsertPackaging.execute();

                    try (ResultSet generatedKeys = psInsertPackaging.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            packagingId = generatedKeys.getInt(1);
                        }
                    } catch (Exception e){
                        throw new SQLException(e);
                    }
                } catch (Exception e){
                    throw new SQLException(e);
                }

                String sqlStorageInsert = "INSERT INTO Storage(plant_id, packaging_id) " +
                        "VALUES (?, ?);";

                try (PreparedStatement psStorageInsert = conn.getConnection().prepareStatement(sqlStorageInsert)) {
                    psStorageInsert.setInt(1, storageRecord.getPlant().getId());
                    psStorageInsert.setInt(2, packagingId);

                    psStorageInsert.execute();
                } catch (Exception e){
                    throw new SQLException(e);
                }
            }

            conn.getConnection().commit();

        } catch (SQLException e){
            try{
                conn.getConnection().rollback();
            } catch (Exception ee){
                throw new RuntimeException("There has been a problem with rolling back the transaction of bulk insert", ee);
            }
            throw new RuntimeException("There has been a problem with bulk inserting the storage records", e);
        } catch (ConnectionException e){
            System.out.println("hello");
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    @Override
    public StorageRecord find(int id) {
        return null;
    }

    /**
     * Inserts a storage record into the database
     * @param item The storage record item to be inserted
     */
    @Override
    public void insert(StorageRecord item) {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            conn.connect();
            conn.getConnection().setAutoCommit(false);

            String sqlGetPlantId = "SELECT id FROM Plant WHERE name = ?;";
            int plantId = -1;
            Plant plant = item.getPlant();

            try (PreparedStatement psGetPlantId = conn.getConnection().prepareStatement(sqlGetPlantId)) {
                psGetPlantId.setString(1, plant.getName());

                ResultSet rs = psGetPlantId.executeQuery();

                if (rs.next()) {
                    plantId = rs.getInt("id");
                }
            } catch (Exception e){
                throw new SQLException(e);
            }

            String sqlInsertPackaging = "INSERT INTO Packaging(expiration_date, number_of_seeds) " +
                    "VALUES (?, ?);";
            int packagingId = 0;
            Packaging packaging = item.getPackaging();

            try (PreparedStatement psInsertPackaging = conn.getConnection().prepareStatement(sqlInsertPackaging, Statement.RETURN_GENERATED_KEYS)) {
                psInsertPackaging.setDate(1, packaging.getExpirationDate());
                psInsertPackaging.setInt(2, packaging.getNumberOfSeeds());

                psInsertPackaging.execute();

                try (ResultSet generatedKeys = psInsertPackaging.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        packagingId = generatedKeys.getInt(1);
                    }
                } catch (Exception e){
                    throw new SQLException(e);
                }
            } catch (Exception e){
                throw new SQLException(e);
            }

            String sqlStorageInsert = "INSERT INTO Storage(plant_id, packaging_id) " +
                    "VALUES (?, ?);";

            try (PreparedStatement psStorageInsert = conn.getConnection().prepareStatement(sqlStorageInsert)) {
                psStorageInsert.setInt(1, plantId);
                psStorageInsert.setInt(2, packagingId);

                psStorageInsert.execute();
            } catch (Exception e){
                throw new SQLException(e);
            }

            conn.getConnection().commit();

        } catch (SQLException e){
            try{
                conn.getConnection().rollback();
            } catch (SQLException ee){
                throw new RuntimeException("There has been a problem with rolling back the transaction of insert", ee);
            }
            throw new RuntimeException("There has been a problem with inserting the storage record", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    @Override
    public void update(StorageRecord item) {

    }

    /**
     * Deletes a storage item from the database based on its id
     * @param id The id of the storage record you want to delete
     */
    @Override
    public void delete(int id) {
        DatabaseConnection conn = new DatabaseConnection();
        try{
            conn.connect();

            String sqlDelete = " CALL remove_seeds(?);";

            try(PreparedStatement psDelete = conn.getConnection().prepareStatement(sqlDelete)){
                psDelete.setInt(1, id);

                psDelete.execute();
            } catch (Exception e){
                throw new SQLException(e);
            }

           conn.close();
        } catch (SQLException e){
            throw new RuntimeException("There has been a problem deleting the storage record", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }

    /**
     * Updates the number of seeds in a storage record. It uses a procedure, which will delete the entire record, if the new number
     * of seeds is 0
     * @param storageRecord The storage record you want to update
     * @param newNumberOfSeeds The new number of seeds
     */
    @Override
    public void updateNumberOfSeeds(StorageRecord storageRecord, int newNumberOfSeeds) {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            conn.connect();

            String sqlUpdateNumberOfSeeds = "CALL plant_seeds(?, ?);";

            try (PreparedStatement psUpdateNumberOfSeeds = conn.getConnection().prepareStatement(sqlUpdateNumberOfSeeds)) {
                psUpdateNumberOfSeeds.setInt(1, storageRecord.getPackaging().getId());
                psUpdateNumberOfSeeds.setInt(2, newNumberOfSeeds);

                psUpdateNumberOfSeeds.execute();
            } catch (Exception e){
                throw new SQLException(e);
            }

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem when updating the number of seeds", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(conn.isConnected()) conn.close();
        }
    }
}
