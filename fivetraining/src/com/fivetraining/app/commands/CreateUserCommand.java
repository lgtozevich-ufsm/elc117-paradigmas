package com.fivetraining.app.commands;

import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.items.ConsoleCommand;

public class CreateUserCommand extends ConsoleCommand {
    public CreateUserCommand() {
        addParameter(ConsoleParameter.createString("nome", true));
        addParameter(ConsoleParameter.createInteger("idade", true));
        addParameter(ConsoleParameter.createBoolean("vivo", false));
    }

    @Override
    public String getName() {
        return "criar_usuario";
    }

    @Override
    public void run(ConsoleInteraction interaction) {
        String name = interaction.getArgument("nome").asString();
        int idade = interaction.getArgument("idade").asInteger();
        boolean vivo = interaction.getArgument("vivo").asBoolean();

        interaction.getConsole().writeLine("Nome: " + name);
        interaction.getConsole().writeLine("Idade: " + idade);
        interaction.getConsole().writeLine("Vivo: " + vivo);
    }
}
