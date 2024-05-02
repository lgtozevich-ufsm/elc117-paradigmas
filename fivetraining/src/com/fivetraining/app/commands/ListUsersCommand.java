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

            interaction.getConsole().writeLine("Lista de alunos");
            for (User user : users) {
                interaction.getConsole().writeLine("ID: " + user.getId());
                interaction.getConsole().writeLine("CPF: " + user.getCpf());
                interaction.getConsole().writeLine("Nome: " + user.getName());
                interaction.getConsole().writeLine("Data de Nascimento: " + user.getBirthDate());
                interaction.getConsole().writeLine();

            }
        } catch (SQLException e) {
            throw new ConsoleCommandExecutionException("Erro ao listar alunos" + e.getMessage());
        }
    }
}
