package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

public class SignOutCommand extends ConsoleCommand {
    private final UserSession userSession;

    public SignOutCommand(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public String getName() {
        return "sair";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        interaction.getConsole().writeLine("Você saiu da conta de " + userSession.getAuthenticatedUser().getName() + ".");

        userSession.signOut();
    }
}
