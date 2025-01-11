package com.Commands.Update;

import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.Commands.Command;
import com.utils.HandyTools.HandyTools;
import com.utils.InputChecker.InputChecker;
import com.utils.ScannerWrapper.ScannerWrapper;
import de.vandermeer.asciitable.AsciiTable;
import org.checkerframework.checker.units.qual.A;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class LiquidatePlantCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    private String generatePlantingTable(ArrayList<Planting> plantings){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Choice", "Date Of Plantings", "Number Of Seeds", "Flowerbed Number", "Plant Name");
        table.addRule();

        for (int i = 0; i < plantings.size(); i++){
            Planting currentPlanting = plantings.get(i);
            table.addRow(i, currentPlanting.getDateFrom(), currentPlanting.getNumberOfSeeds(), currentPlanting.getFlowerbed().getNumber(), currentPlanting.getPlant().getName());
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try{
            Scanner sc = ScannerWrapper.getScanner();

            ArrayList<Planting> plantings = plantingDao.findAllPlanted();
            String renderedTable = generatePlantingTable(plantings);

            System.out.println(renderedTable);

            int plantingPick = HandyTools.chooseFromList("Choose a planting by its index", plantings);

            Planting planting = plantings.get(plantingPick);

            Date liquidationDate = HandyTools.pickDate("liquidation date");

            plantingDao.liquidatePlanting(planting.getId(), liquidationDate);

            System.out.println("Plant liquidated successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
