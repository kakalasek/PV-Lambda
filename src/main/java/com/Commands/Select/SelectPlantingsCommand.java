package com.Commands.Select;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Flowerbed.Flowerbed;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.Commands.Command;
import com.DbObjects.Storage.Packaging.Packaging;
import com.DbObjects.Storage.StorageRecord.StorageRecord;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

public class SelectPlantingsCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    private String generatePlantingTable(ArrayList<Planting> plantings){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Date Of Planting","Date Of Liquidation", "Flowerbed Number", "Flowerbed Size", "Number Of Seeds", "Plant Name", "Life Length", "Growing Time", "Spacing", "Planting Depth", "Planting Time", "Pre Growing");
        table.addRule();

        for (Planting planting : plantings){
            Flowerbed flowerbed = planting.getFlowerbed();
            Plant plant = planting.getPlant();
            table.addRow(planting.getDateFrom(), planting.getDateTo(), planting.getNumberOfSeeds(), flowerbed.getNumber(), flowerbed.getSize(), plant.getName(), plant.getLifeLength(), plant.getGrowingTime(), plant.getSpacing(), plant.getPlantingDepth(), plant.getPlantingTime(), plant.needsPreGrowing());
        }

        table.addRule();

        return table.render();
    }

    @Override
    public void execute() {
        try {
            ArrayList<Planting> plantings = plantingDao.findAll();

            String renderedTable = generatePlantingTable(plantings);

            System.out.println(renderedTable);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
