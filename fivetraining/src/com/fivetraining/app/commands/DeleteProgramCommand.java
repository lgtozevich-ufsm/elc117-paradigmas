package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class DeleteProgramCommand extends ConsoleCommand {
    private final ProgramDAO programDAO;

    public DeleteProgramCommand(ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO) {
        this.programDAO = programDAO;

        addParameter(ConsoleParameter.createInteger("id do programa", true));
    }

    @Override
    public String getName() {
        return "del-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int programId = interaction.getArgument("id do programa").asInteger();

        try {
            Program program = programDAO.findById(programId);

            if (program == null) {
                throw new ConsoleCommandExecutionException("Nenhum programa com o id \"" + programId + "\" foi encontrado");
            }

            programDAO.delete(program);

            interaction.getConsole().writeLine("O programa \"" + program.getName() + "\" foi excluído com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
