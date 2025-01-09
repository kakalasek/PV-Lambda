package com.MainLoop.Examples;

import com.MainLoop.Command;

public class NonRepeatableReadExampleCommand implements Command {
    @Override
    public void execute() {
        System.out.println("This si an example showing the concept of Non-Repeatable Read");
    }
}
