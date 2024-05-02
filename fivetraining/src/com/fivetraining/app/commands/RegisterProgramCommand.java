package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RegisterProgramCommand extends ConsoleCommand {
    private final ProgramDAO programDAO;
    private final UserDAO userDAO;

    public RegisterProgramCommand(ProgramDAO programDAO, UserDAO userDAO) {
        this.programDAO = programDAO;
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf aluno", true));
        addParameter(ConsoleParameter.createString("nome programa", true));
    }

    @Override
    public String getName() {
        return "reg-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf aluno").asString();
        String programName = interaction.getArgument("nome programa").asString();

        try {
            int studentId = userDAO.findByCpf(cpf).getId();

            Program program = new Program();
            program.setUserId(studentId);
            program.setName(programName);

            programDAO.insert(program);

            interaction.getConsole().writeLine("Programa registrado com sucesso para o aluno com CPF: " + cpf);
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Erro ao registrar programa: " + exception.getMessage());
        }
    }
}
