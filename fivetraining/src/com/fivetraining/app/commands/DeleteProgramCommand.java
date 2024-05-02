package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class DeleteProgramCommand extends ConsoleCommand {
    private final ProgramDAO programDAO;
    private final ProgramExerciseDAO programExerciseDAO;

    public DeleteProgramCommand(ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO) {
        this.programDAO = programDAO;
        this.programExerciseDAO = programExerciseDAO;

        addParameter(ConsoleParameter.createInteger("codigo prog", true));
    }

    @Override
    public String getName() {
        return "del-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int programId = interaction.getArgument("codigo prog").asInteger();

        try {

            programExerciseDAO.deleteByProgramId(programId);

            programDAO.delete(programId);

            interaction.getConsole().writeLine("Programa com código " + programId + " foi excluído com sucesso");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Erro ao excluir o programa: " + exception.getMessage());
        }
    }
}
