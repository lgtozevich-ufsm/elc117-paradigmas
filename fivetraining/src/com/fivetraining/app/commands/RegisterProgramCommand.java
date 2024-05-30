package com.fivetraining.app.commands;

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
    private final UserDAO userDAO;
    private final ProgramDAO programDAO;

    public RegisterProgramCommand(UserDAO userDAO, ProgramDAO programDAO) {
        this.userDAO = userDAO;
        this.programDAO = programDAO;

        addParameter(ConsoleParameter.createString("cpf do aluno", true));
        addParameter(ConsoleParameter.createString("nome do programa", true));
    }

    @Override
    public String getName() {
        return "reg-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf do aluno").asString();
        String name = interaction.getArgument("nome do programa").asString();

        try {
            User user = userDAO.findByCPF(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o CPF \"" + cpf + "\" foi encontrado");
            }

            Program program = new Program();
            program.setUserId(user.getId());
            program.setName(name);

            programDAO.insert(program);

            interaction.getConsole().writeLine("O programa foi registrado com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  id: " + program.getId());
            interaction.getConsole().writeLine("o  id do usuário: " + program.getUserId());
            interaction.getConsole().writeLine("`- nome: " + program.getName());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
