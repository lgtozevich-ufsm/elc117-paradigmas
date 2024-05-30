package com.fivetraining.app.commands;

import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterUserCommand extends ConsoleCommand {
    private final UserDAO userDAO;

    public RegisterUserCommand(UserDAO userDAO) {
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf", true));
        addParameter(ConsoleParameter.createString("nome", true));
        addParameter(ConsoleParameter.createDate("data de nascimento", true));
    }

    @Override
    public String getName() {
        return "reg-aluno";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf").asString();
        String name = interaction.getArgument("nome").asString();
        LocalDate birthDate = interaction.getArgument("data de nascimento").asDate();

        User user = new User();
        user.setCPF(cpf);
        user.setName(name);
        user.setBirthDate(birthDate);

        try {
            userDAO.insert(user);

            interaction.getConsole().writeLine("O aluno foi registrado com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  id: " + user.getId());
            interaction.getConsole().writeLine("|  cpf: " + user.getCPF());
            interaction.getConsole().writeLine("|  nome: " + user.getName());
            interaction.getConsole().writeLine("`- data de nascimento: " + user.getBirthDate());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Um erro ocorreu ao registrar o aluno: " + exception);
        }
    }
}
