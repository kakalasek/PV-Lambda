package com.MainLoop;

public class NotImplementedCommand implements Command{

    @Override
    public void execute() {
        System.out.println("This command is not yet implemented");
    }
}
