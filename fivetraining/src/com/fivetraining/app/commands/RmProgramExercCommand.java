package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RmProgramExercCommand extends ConsoleCommand {
    private final ProgramExerciseDAO programExerciseDAO;
    private final UserSession userSession;

    public RmProgramExercCommand(ProgramExerciseDAO programExerciseDAO, UserSession userSession) {
        this.programExerciseDAO = programExerciseDAO;
        this.userSession = userSession;

        addParameter(ConsoleParameter.createInteger("id programa", true));
        addParameter(ConsoleParameter.createInteger("codigo exercicio", true));
    }

    @Override
    public String getName() {
        return "rm-exerc-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        int programId = interaction.getArgument("id programa").asInteger();
        int exerciseId = interaction.getArgument("codigo exercicio").asInteger();

        try {

            programExerciseDAO.delete(programId, exerciseId);

            interaction.getConsole().writeLine("Exercício removido do programa com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Erro ao remover exercício do programa: " + exception.getMessage());
        }
    }
}
