package com.DbObjects.Examples;

import com.CustomExceptions.ConnectionException;
import com.Database.DatabaseConnection;
import com.DbObjects.Storage.Packaging.Packaging;

import java.sql.*;

/**
 * This DAO is a little special. It is supposed to by used by only the examples
 * in this program. The methods here are not meant to make much sense, only
 * to cooperate with the intention of the examples
 */
public class ExamplesDaoImpl implements ExamplesDao{

    /**
     * Will return the number the seeds inside the packaging
     * @param id ID of the packaging
     * @param connection The connection which will perform the query
     * @return The number of seeds inside the package
     */
    @Override
    public int readNumberOfSeedsInPackaging(int id, Connection connection) {
        try {
            int numberOfSeeds = 0;

            String sqlSelectById = "SELECT number_of_seeds FROM Packaging WHERE id = ?;";

            try(PreparedStatement psSelectById = connection.prepareStatement(sqlSelectById)){
                psSelectById.setInt(1, id);

                ResultSet rs = psSelectById.executeQuery();

                if(rs.next()){
                    numberOfSeeds = rs.getInt("number_of_seeds");
                }
            }

            return numberOfSeeds;
        } catch (Exception e){
            throw new RuntimeException("There has been a problem with reading the number of seeds in the package", e);
        }
    }

    /**
     * Updates a packaging by writing a new number of seeds
     * @param id ID of the packaging to be updated
     * @param newNumberOfSeeds The new number of seeds
     * @param connection The connection which will perform the query
     */
    @Override
    public void writeNumberOfSeedsInPackaging(int id, int newNumberOfSeeds, Connection connection) {
        try {

            String sqlSelectById = "UPDATE Packaging SET number_of_seeds = ? WHERE id = ?;";

            try(PreparedStatement psSelectById = connection.prepareStatement(sqlSelectById)){
                psSelectById.setInt(1, newNumberOfSeeds);
                psSelectById.setInt(2, id);

                psSelectById.execute();
            }

        } catch (Exception e){
            throw new RuntimeException("There has been a problem with writing the number of seeds in the package", e);
        }
    }

    /**
     * Inserts a packaging into a database and returns its id
     * @param packaging The packaging that will be inserted
     * @param connection The connection which will be used in the process
     * @return The id of the inserted packaging
     */
    @Override
    public int insertPackagingAndRetrieveId(Packaging packaging, Connection connection) {
        try{
        String sqlInsertPackaging = "INSERT INTO Packaging(expiration_date, number_of_seeds) " +
                "VALUES (?, ?);";
        int packagingId = 0;

        try (PreparedStatement psInsertPackaging = connection.prepareStatement(sqlInsertPackaging, Statement.RETURN_GENERATED_KEYS)) {
            psInsertPackaging.setDate(1, packaging.getExpirationDate());
            psInsertPackaging.setInt(2, packaging.getNumberOfSeeds());

            psInsertPackaging.execute();

            try (ResultSet generatedKeys = psInsertPackaging.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    packagingId = generatedKeys.getInt(1);
                }
            }
        }

        return packagingId;
        }catch (SQLException e) {
            throw new RuntimeException("There has been a problem inserting the packaging", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Simply deletes a packaging by its id
     * @param id The id of the packaging you want to delete
     * @param connection The connection which will perform the query
     */
    @Override
    public void deletePackaging(int id, Connection connection) {
        try{
            String sqlDelete = "DELETE FROM Packaging WHERE id = ?;";

            try(PreparedStatement psDelete = connection.prepareStatement(sqlDelete)){
                psDelete.setInt(1, id);

                psDelete.execute();
            }

        } catch (SQLException e){
            throw new RuntimeException("There has been a problem deleting the packaging", e);
        } catch (ConnectionException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
