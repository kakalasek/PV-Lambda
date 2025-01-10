package com.MainLoop.Menu.MenuItem;

import com.Commands.Command;

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
