package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddExerciseToProgramCommand extends ConsoleCommand {
    private final ProgramExerciseDAO programExerciseDAO;
    private final UserSession userSession;

    private  final ProgramDAO programDAO;

    public AddExerciseToProgramCommand(ProgramExerciseDAO programExerciseDAO, UserSession userSession, ProgramDAO programDAO) {
        this.programExerciseDAO = programExerciseDAO;
        this.userSession = userSession;
        this.programDAO = programDAO;

        addParameter(ConsoleParameter.createInteger("id programa", true));
        addParameter(ConsoleParameter.createInteger("codigo exercicio", true));
        addParameter(ConsoleParameter.createInteger("series", true));
        addParameter(ConsoleParameter.createInteger("repeticoes minima", true));
        addParameter(ConsoleParameter.createInteger("repeticoes maxima", true));
        addParameter(ConsoleParameter.createInteger("carga", true));
        addParameter(ConsoleParameter.createDouble("descanso", true));
    }

    @Override
    public String getName() {
        return "add-exerc-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {

        userSession.throwIfNotAuthenticated();

        int programId = interaction.getArgument("id programa").asInteger();
        int exerciseCode = interaction.getArgument("codigo exercicio").asInteger();
        int sets =interaction.getArgument("series").asInteger();
        int minRepetitions = interaction.getArgument("repeticoes minima").asInteger();
        int maxRepetitions = interaction.getArgument("repeticoes maxima").asInteger();
        int load = interaction.getArgument("carga").asInteger();
        double restingTime = interaction.getArgument("descanso").asDouble();

        try {
            Program program = programDAO.findById(programId);
            if (program == null) {
                throw new ConsoleCommandExecutionException("O programa com ID " + programId + " não existe.");
            }
            ProgramExercise programExercise = new ProgramExercise();
            programExercise.setProgramId(programId);
            programExercise.setExerciseCode(exerciseCode);
            programExercise.setSets(sets);
            programExercise.setMinimumRepetitions(minRepetitions);
            programExercise.setMaximumRepetitions(maxRepetitions);
            programExercise.setLoad(load);
            programExercise.setRestingTime(restingTime);

            programExerciseDAO.insert(programExercise);

            interaction.getConsole().writeLine("Exercício adicionado ao programa com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Erro ao adicionar exercício ao programa: " + exception.getMessage());
        }
    }
}
