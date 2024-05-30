package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class AddProgramExerciseCommand extends ConsoleCommand {
    private final ExerciseDAO exerciseDAO;
    private final ProgramDAO programDAO;
    private final ProgramExerciseDAO programExerciseDAO;

    public AddProgramExerciseCommand(ExerciseDAO exerciseDAO, ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO) {
        this.exerciseDAO = exerciseDAO;
        this.programDAO = programDAO;
        this.programExerciseDAO = programExerciseDAO;

        addParameter(ConsoleParameter.createInteger("id do programa", true));
        addParameter(ConsoleParameter.createInteger("código do exercício", true));
        addParameter(ConsoleParameter.createInteger("carga", true));
        addParameter(ConsoleParameter.createInteger("n° de séries", true));
        addParameter(ConsoleParameter.createInteger("n° mínimo de repetições", true));
        addParameter(ConsoleParameter.createInteger("n° máximo de repetições", true));
        addParameter(ConsoleParameter.createDouble("minutos de descanso", true));
    }

    @Override
    public String getName() {
        return "add-exerc-prog";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int programId = interaction.getArgument("id do programa").asInteger();
        int exerciseCode = interaction.getArgument("código do exercício").asInteger();
        int load = interaction.getArgument("carga").asInteger();
        int sets = interaction.getArgument("n° de séries").asInteger();
        int minimumRepetitions = interaction.getArgument("n° mínimo de repetições").asInteger();
        int maximumRepetitions = interaction.getArgument("n° máximo de repetições").asInteger();
        double restingTime = interaction.getArgument("minutos de descanso").asDouble();

        try {
            Program program = programDAO.findById(programId);

            if (program == null) {
                throw new ConsoleCommandExecutionException("Nenhum programa com o id \"" + programId + "\" foi encontrado");
            }

            Exercise exercise = exerciseDAO.findByCode(exerciseCode);

            if (exercise == null) {
                throw new ConsoleCommandExecutionException("Nenhum exercício com o código \"" + exerciseCode + "\" foi encontrado");
            }

            ProgramExercise programExercise = new ProgramExercise();
            programExercise.setProgramId(programId);
            programExercise.setExerciseCode(exerciseCode);
            programExercise.setLoad(load);
            programExercise.setSets(sets);
            programExercise.setMinimumRepetitions(minimumRepetitions);
            programExercise.setMaximumRepetitions(maximumRepetitions);
            programExercise.setRestingTime(restingTime);

            programExerciseDAO.insert(programExercise);

            interaction.getConsole().writeLine("O exercício \"" + exercise.getName() + "\" foi adicionado ao programa com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  código: " + exercise.getCode());
            interaction.getConsole().writeLine("|  nome: " + exercise.getName());
            interaction.getConsole().writeLine("|  músculos: " + exercise.getMuscles());
            interaction.getConsole().writeLine("|  carga: " + programExercise.getLoad());
            interaction.getConsole().writeLine("|  n° de séries: " + programExercise.getSets());
            interaction.getConsole().writeLine("|  n° mínimo de repetições: " + programExercise.getMinimumRepetitions());
            interaction.getConsole().writeLine("|  n° máximo de repetições: " + programExercise.getMaximumRepetitions());
            interaction.getConsole().writeLine("`- minutos de descanso: " + programExercise.getRestingTime());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
