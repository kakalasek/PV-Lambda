package com.Commands.Select;

import com.DbObjects.Plant.Plant;
import com.DbObjects.Planting.Flowerbed.Flowerbed;
import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.Commands.Command;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

/**
 * Command which displays all the plantings inside the database
 */
public class SelectPlantingsCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    /**
     * Will generate a simple table of plantings
     * @param plantings A list of plantings
     * @return The rendered table string
     */
    private String generatePlantingTable(ArrayList<Planting> plantings){

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Date Of Planting", "Date Of Liquidation", "Number Of Seeds", "Flowerbed Number", "Flowerbed Size m^2", "Plant Name", "Life Length", "Growing Time (day)", "Spacing cm", "Planting Depth cm", "Planting Time (month)", "Pre Growing");
        table.addRule();

        for (Planting planting : plantings){
            Flowerbed flowerbed = planting.getFlowerbed();
            Plant plant = planting.getPlant();
            String dateTo = planting.getDateTo() != null ? planting.getDateTo().toString() : "Still planted";
            table.addRow(planting.getDateFrom(), dateTo, planting.getNumberOfSeeds(), flowerbed.getNumber(), flowerbed.getSize(), plant.getName(), plant.getLifeLength(), plant.getGrowingTime(), plant.getSpacing(), plant.getPlantingDepth(), plant.getPlantingTime(), plant.needsPreGrowing());
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
