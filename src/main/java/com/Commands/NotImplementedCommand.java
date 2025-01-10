package com.Commands;

/**
 * Is here only for one purpose. If anyone wanted to enter a command somewhere, but did not yet have its
 * implementation programmed.
 */
public class NotImplementedCommand implements Command {

    @Override
    public void execute() {
        System.out.println("This command is not yet implemented");
    }
}
