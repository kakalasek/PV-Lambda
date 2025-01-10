package com.MainLoop.Menu.MenuItem;

import com.Commands.Command;

/**
 * This class represents an item inside a menu.
 * It has a message, that can be displayed, and a command, which can be executed
 */
public class MenuItem {

    private String message;
    private Command command;

    public MenuItem(String message, Command command){
        this.message = message;
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void executeCommand(){
        command.execute();
    }
}
