package commands;

import console.ConsoleGuard;
import console.ConsoleInteraction;
import console.items.ConsoleCommand;
import console.items.ConsoleDisplayable;

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
