package commands;

import console.ConsoleInteraction;
import console.items.ConsoleCommand;

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
