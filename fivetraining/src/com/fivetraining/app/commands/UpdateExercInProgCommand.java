package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class UpdateExercInProgCommand extends ConsoleCommand {
    private final ProgramExerciseDAO programExerciseDAO;
    private final UserSession userSession;

    public UpdateExercInProgCommand(ProgramExerciseDAO programExerciseDAO, UserSession userSession) {
        this.programExerciseDAO = programExerciseDAO;
        this.userSession = userSession;

        addParameter(ConsoleParameter.createInteger("codigo programa", true));
        addParameter(ConsoleParameter.createInteger("codigo exercicio", true));
        addParameter(ConsoleParameter.createInteger("carga", true));

    }

    @Override
    public String getName() {
        return "alt-exerc-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        int programId = interaction.getArgument("codigo programa").asInteger();
        int exerciseId = interaction.getArgument("codigo exercicio").asInteger();
        int load = interaction.getArgument("carga").asInteger();

        try {
            ProgramExercise programExercise = programExerciseDAO.findByProgramAndExerciseId(programId, exerciseId);

            if (programExercise == null) {
                throw new ConsoleCommandExecutionException("Exercício não encontrado neste programa.");
            }

            programExercise.setLoad(load);

            programExerciseDAO.update(programExercise);

            interaction.getConsole().writeLine("Exercício no programa atualizado com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Erro ao atualizar exercício no programa: " + exception.getMessage());
        }
    }
}
