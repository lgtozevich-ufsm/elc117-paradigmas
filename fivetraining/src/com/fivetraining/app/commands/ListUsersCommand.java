package com.fivetraining.app.commands;

import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListUsersCommand extends ConsoleCommand {
    private final UserDAO userDAO;

    public ListUsersCommand(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String getName() {
        return "list-alunos";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        try {
            List<User> users = userDAO.findAll();

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
