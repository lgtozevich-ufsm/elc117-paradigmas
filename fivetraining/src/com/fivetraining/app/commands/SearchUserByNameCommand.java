package com.fivetraining.app.commands;

import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class SearchUserByNameCommand extends ConsoleCommand {
    private final UserDAO userDAO;

    public SearchUserByNameCommand(UserDAO userDAO) {
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("nome parcial", true));
    }

    @Override
    public String getName() {
        return "busc-aluno-nome";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String name = interaction.getArgument("nome parcial").asString();

        try {
            List<User> users = userDAO.findAllWithPartialName(name);

            for (User user : users) {
                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o  id: " + user.getId());
                interaction.getConsole().writeLine("|  cpf: " + user.getCPF());
                interaction.getConsole().writeLine("|  nome: " + user.getName());
                interaction.getConsole().writeLine("`- data de nascimento: " + user.getBirthDate());
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
