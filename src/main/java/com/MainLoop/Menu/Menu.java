package com.MainLoop.Menu;

import com.CustomExceptions.NumberNotWithinOptionsException;
import com.CustomExceptions.RowCharactersExceededException;
import com.MainLoop.Command;
import com.MainLoop.Menu.MenuItem.MenuItem;

import java.util.ArrayList;

public class Menu {

    private final int numberOfCharactersInOneRow = 30;
    private final ArrayList<MenuItem> menuItems;

    public Menu(){
        menuItems = new ArrayList<>();
    }

    public String display(){

        final String menuTop = """
                
                +--------------------------------+
                | Select one of these options    |
                | ============================== |
                """;

        final String menuBottom = """
                +--------------------------------+
                
                """;

        StringBuilder menuString = new StringBuilder();
        menuString.append(menuTop);

        for(MenuItem menuItem : menuItems){
            String menuItemMessage = menuItem.getMessage();
            int menuItemMessageTrailingSpacesNumber = numberOfCharactersInOneRow - menuItemMessage.length();
            String menuItemMessageTrailingSpaces = " ".repeat(menuItemMessageTrailingSpacesNumber);
            menuString.append("| ").append(menuItem.getMessage()).append(menuItemMessageTrailingSpaces).append(" |\n");
        }

        menuString.append(menuBottom);

        return menuString.toString();
    }

    public void registerItem(String message, Command command) {
        if(message.length() > numberOfCharactersInOneRow){
            throw new RowCharactersExceededException("This message is too long for this menu");
        }
        menuItems.add(new MenuItem(message, command));
    }

    public void selectItem(int selectedItem) throws NumberNotWithinOptionsException {
        if(selectedItem > menuItems.size()){
            throw new NumberNotWithinOptionsException("This number does not correspond to any menu item");
        }
        menuItems.get(selectedItem).executeCommand();
    }
}
