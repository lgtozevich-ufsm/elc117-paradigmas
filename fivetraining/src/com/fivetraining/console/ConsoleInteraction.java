package com.fivetraining.console;

import java.util.Map;

public class ConsoleInteraction {
    private final Console console;
    private final Map<String, ConsoleArgument> arguments;

    public ConsoleInteraction(Console console, Map<String, ConsoleArgument> arguments) {
        this.console = console;
        this.arguments = arguments;
    }

    public Console getConsole() {
        return console;
    }

    public ConsoleArgument getArgument(String name) {
        return arguments.get(name);
    }
}
