package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RemoveProgramExerciseCommand extends ConsoleCommand {
    private final ExerciseDAO exerciseDAO;
    private final ProgramExerciseDAO programExerciseDAO;

    public RemoveProgramExerciseCommand(ExerciseDAO exerciseDAO, ProgramExerciseDAO programExerciseDAO) {
        this.exerciseDAO = exerciseDAO;
        this.programExerciseDAO = programExerciseDAO;

        addParameter(ConsoleParameter.createInteger("id do programa", true));
        addParameter(ConsoleParameter.createInteger("código do exercício", true));
    }

    @Override
    public String getName() {
        return "rm-exerc-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int programId = interaction.getArgument("id do programa").asInteger();
        int exerciseCode = interaction.getArgument("código do exercício").asInteger();

        try {
            ProgramExercise programExercise = programExerciseDAO.findByProgramIdAndExerciseCode(programId, exerciseCode);

            if (programExercise == null) {
                throw new ConsoleCommandExecutionException("Nenhum exercício com o código \"" + exerciseCode + "\" foi encontrado no programa com o id \"" + programId + "\"");
            }

            Exercise exercise = exerciseDAO.findByCode(exerciseCode);

            programExerciseDAO.delete(programExercise);

            interaction.getConsole().writeLine("O exercício \"" + exercise.getName() + "\" foi removido do programa com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
