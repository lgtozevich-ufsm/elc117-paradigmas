package com.fivetraining.app.commands;

import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class DeleteUserCommand extends ConsoleCommand {
    private final UserDAO userDAO;

    public DeleteUserCommand(UserDAO userDAO) {
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf", true));
    }

    @Override
    public String getName() {
        return "del-aluno";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf").asString();

        try {
            User user = userDAO.findByCPF(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o cpf \"" + cpf + "\" foi encontrado");
            }

            userDAO.delete(user);

            interaction.getConsole().writeLine("O usuário \"" + user.getName() + "\" foi excluído com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
