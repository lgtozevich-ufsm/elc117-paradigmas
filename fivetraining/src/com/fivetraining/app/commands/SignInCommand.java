package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class SignInCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final UserDAO userDAO;

    public SignInCommand(UserSession userSession, UserDAO userDAO) {
        this.userSession = userSession;
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf", true));
    }

    @Override
    public String getName() {
        return "entrar";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf").asString();

        try {
            User user = userDAO.findByCPF(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o cpf \"" + cpf + "\" foi encontrado");
            }

            userSession.signIn(user);

            interaction.getConsole().writeLine("Você entrou na conta de " + user.getName() + ".");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
