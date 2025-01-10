package com.MainLoop.Menu;

import com.CustomExceptions.InvalidOptionException;
import com.CustomExceptions.RowCharactersExceededException;
import com.Commands.Command;
import com.MainLoop.Menu.MenuItem.MenuItem;

import java.util.ArrayList;

/**
 * Class for dynamically building a simple menu. You can register menu items with various commands
 */
public class Menu {

    private final int maxMessageCharacters = 30;    // DO NOT MAKE THIS NUMBER SMALLER THAN 30!
    private final ArrayList<MenuItem> menuItems;

    public Menu(){
        menuItems = new ArrayList<>();
    }

    /**
     * To fasten things up, this method builds the menu prompt once, so it can be printed by
     * the method using it
     * @return A string containing the menu prompt
     */
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

    /**
     * Register a new menu item. In the order in which you register the menu items, they will be displayed
     * in the menu prompt
     * @param message The message, which will be displayed for this option. It has a finite character size.
     *                You can find it and change it in this class, however don't make it smaller than 30
     * @param command A class which implements the Command interface. It is the action the menu option will
     *                perform when chosen
     * @throws RowCharactersExceededException It is thrown, if the message exceeds the maximum allowed number of
     *                                        characters
     */
    public void registerItem(String message, Command command) {
        if(message.length() > maxMessageCharacters){
            throw new RowCharactersExceededException("This message is too long for this menu");
        }
        menuItems.add(new MenuItem(message, command));
    }

    /**
     * Used to select an item. It basically just executes the items executeCommand method
     * @param selectedItem The index of the item, you want to select
     * @throws InvalidOptionException Is thrown when the selectedItem is either not registered, or the number is negative
     */
    public void selectItem(int selectedItem) throws InvalidOptionException {
        if(selectedItem > menuItems.size() || selectedItem < 0){
            throw new InvalidOptionException("This number does not correspond to any menu item");
        }
        menuItems.get(selectedItem).executeCommand();
    }
}
