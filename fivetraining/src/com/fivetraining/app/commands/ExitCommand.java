package com.fivetraining.app.commands;

import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.items.ConsoleCommand;

public class ExitCommand extends ConsoleCommand {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void run(ConsoleInteraction interaction) {
        interaction.getConsole().writeLine("Saindo...");
        System.exit(0);
    }
}
