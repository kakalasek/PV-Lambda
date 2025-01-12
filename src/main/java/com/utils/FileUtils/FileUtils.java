package com.utils.FileUtils;

import com.CustomExceptions.CsvReadException;
import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    /**
     * Reads a csv file and returns its representation using Lists
     * @param filename The name of the file you want to read
     * @return A List of Lists of Strings. Each List within the main List represents a row and each string a value in a column
     * @throws CsvReadException Is thrown if any problem occurs during reading the file
     */
    public static List<List<String>> readCsvFile(String filename) throws CsvReadException {
        try {
            List<List<String>> records = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
                    String[] values;
                    while ((values = csvReader.readNext()) != null) {
                        records.add(Arrays.asList(values));
                    }
                }

            return records;
        } catch (Exception e){
            throw new CsvReadException("There has been a problem reading the csv file", e);
        }
    }

    /**
     * Appends text to a file
     * @param text The text you want to append
     * @param filename The name of the file you want to append to
     * @throws IOException Is thrown if appending to the file goes wrong by any reason
     */
    public static void appendToFile(String text, String filename) throws IOException{
        try{
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))){
                writer.write(text);
            }
        } catch (IOException e){
            throw new IOException("There has been a problem with appending data to the file", e);
        }
    }

    /**
     * Simply clears the file by writing an empty string to it
     * @param filename The name of the file about to be cleared
     * @throws IOException Is thrown if anything wrong happens when reading the file
     */
    public static void clearFile(String filename) throws IOException{
       try{
           try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
               writer.write("");
           }
       } catch (IOException e){
           throw new IOException("There has been a problem with clearing the file", e);
       }
    }
}
