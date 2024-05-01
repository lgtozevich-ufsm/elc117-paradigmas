package com.fivetraining.app.commands;

import com.fivetraining.console.ConsoleGuard;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.items.ConsoleCommand;
import com.fivetraining.console.items.ConsoleDisplayable;

public class HelpCommand extends ConsoleCommand {
    private final ConsoleGuard guard;

    public HelpCommand(ConsoleGuard guard) {
        this.guard = guard;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void run(ConsoleInteraction interaction) {
        interaction.getConsole().writeLine("Comandos:");

        for (ConsoleDisplayable item : guard.getItems()) {
            interaction.getConsole().writeLine(item.display());
        }
    }
}
