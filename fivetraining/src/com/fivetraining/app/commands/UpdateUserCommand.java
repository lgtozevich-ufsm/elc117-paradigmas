package com.fivetraining.app.commands;

import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateUserCommand extends ConsoleCommand {
    private final UserDAO userDAO;

    public UpdateUserCommand(UserDAO userDAO) {
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf", true));
        addParameter(ConsoleParameter.createString("novo cpf", true));
        addParameter(ConsoleParameter.createString("novo nome", true));
        addParameter(ConsoleParameter.createDate("nova data de nascimento", true));
    }

    @Override
    public String getName() {
        return "alt-aluno";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf").asString();
        String newCPF = interaction.getArgument("novo cpf").asString();
        String newName = interaction.getArgument("novo nome").asString();
        LocalDate newBirthDate = interaction.getArgument("nova data de nascimento").asDate();

        try {
            User user = userDAO.findByCPF(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o cpf \"" + cpf + "\" foi encontrado");
            }

            user.setCPF(newCPF);
            user.setName(newName);
            user.setBirthDate(newBirthDate);

            userDAO.update(user);

            interaction.getConsole().writeLine("O usuário \"" + user.getName() + "\" foi atualizado com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  id: " + user.getId());
            interaction.getConsole().writeLine("|  cpf: " + user.getCPF());
            interaction.getConsole().writeLine("|  nome: " + user.getName());
            interaction.getConsole().writeLine("`- data de nascimento: " + user.getBirthDate());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
