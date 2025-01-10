package com.MainLoop.Menu;

import com.CustomExceptions.InvalidOptionException;
import com.CustomExceptions.RowCharactersExceededException;
import com.Commands.Command;
import com.MainLoop.Menu.MenuItem.MenuItem;

import java.util.ArrayList;

public class Menu {

    private final int maxMessageCharacters = 30;    // DO NOT MAKE THIS NUMBER SMALLER THAN 30!
    private final ArrayList<MenuItem> menuItems;

    public Menu(){
        menuItems = new ArrayList<>();
    }

    public String buildMenuPrompt(){
        StringBuilder menuString = new StringBuilder();
        int menuItemIndex = 0;

        int maxMenuItemIndexNumberOfDigits = String.valueOf(menuItems.size()).length();

        final String topPrompt = "Select One Of These Options";

        int numberOfCharactersBetween = maxMenuItemIndexNumberOfDigits + 2 + maxMessageCharacters;
        int topPromptTrailingSpacesNumber = numberOfCharactersBetween - topPrompt.length();

        menuString.append("\n");
        menuString.append("+-").append("-".repeat(numberOfCharactersBetween)).append("-+\n");
        menuString.append("| ").append(topPrompt).append(" ".repeat(topPromptTrailingSpacesNumber)).append(" |\n");
        menuString.append("| ").append("=".repeat(numberOfCharactersBetween)).append(" |\n");

        for(MenuItem menuItem : menuItems){
            String menuItemMessage = menuItemIndex + ") " + menuItem.getMessage();
            int menuItemMessageTrailingSpacesNumber = numberOfCharactersBetween - menuItemMessage.length();
            String menuItemMessageTrailingSpaces = " ".repeat(menuItemMessageTrailingSpacesNumber);

            menuString.append("| ").append(menuItemMessage).append(menuItemMessageTrailingSpaces).append(" |\n");
            menuItemIndex++;
        }

        menuString.append("+-").append("-".repeat(numberOfCharactersBetween)).append("-+\n\n");

        return menuString.toString();
    }

    public void registerItem(String message, Command command) {
        if(message.length() > maxMessageCharacters){
            throw new RowCharactersExceededException("This message is too long for this menu");
        }
        menuItems.add(new MenuItem(message, command));
    }

    public void selectItem(int selectedItem) throws InvalidOptionException {
        if(selectedItem > menuItems.size()){
            throw new InvalidOptionException("This number does not correspond to any menu item");
        }
        menuItems.get(selectedItem).executeCommand();
    }
}
