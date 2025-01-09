package com.MainLoop.Select;

import com.DbObjects.Planting.Planting.Planting;
import com.DbObjects.Planting.Planting.PlantingDaoImpl;
import com.MainLoop.Command;

import java.util.ArrayList;

public class SelectPlantingsCommand implements Command {

    PlantingDaoImpl plantingDao = new PlantingDaoImpl();

    @Override
    public void execute() {
        try {
            ArrayList<Planting> plantings = plantingDao.findAll();
            StringBuilder output = new StringBuilder("Date Of Planting | Date Of Liquidation | Number Of Seeds | Flowerbed Size | Flowerbed Number | Plant Name | Life Length | Growing Time | Spacing | Planting Depth | Planting Time | Pre Growing \n");
            for (Planting planting : plantings) {
                output.append(planting.toString()).append("\n");
            }
            System.out.println(output.toString());
        } catch (Exception e){
            System.out.println("There has been a problem");
        }
    }
}
