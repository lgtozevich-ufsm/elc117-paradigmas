package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RegisterProgramCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ProgramDAO programDAO;

    public RegisterProgramCommand(UserSession userSession, ProgramDAO programDAO) {
        this.userSession = userSession;
        this.programDAO = programDAO;

        addParameter(ConsoleParameter.createString("nome", true));
    }

    @Override
    public String getName() {
        return "reg-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        String name = interaction.getArgument("nome").asString();

        try {
            Program program = new Program();
            program.setUserId(userSession.getAuthenticatedUser().getId());
            program.setName(name);

            programDAO.insert(program);

            interaction.getConsole().writeLine("O programa foi registrado com sucesso!");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
