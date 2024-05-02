package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RegisterExerciseCommand extends ConsoleCommand {
    private final ExerciseDAO exerciseDAO;

    public RegisterExerciseCommand(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;

        addParameter(ConsoleParameter.createInteger("código", true));
        addParameter(ConsoleParameter.createString("nome", true));
        addParameter(ConsoleParameter.createString("músculos", true));
    }

    @Override
    public String getName() {
        return "reg-exerc";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int code = interaction.getArgument("código").asInteger();
        String name = interaction.getArgument("nome").asString();
        String muscles = interaction.getArgument("músculos").asString();

        Exercise exercise = new Exercise();
        exercise.setCode(code);
        exercise.setName(name);
        exercise.setMuscles(muscles);

        try {
            exerciseDAO.insert(exercise);

            interaction.getConsole().writeLine("O exercício foi registrado com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  código: " + exercise.getCode());
            interaction.getConsole().writeLine("|  nome: " + exercise.getName());
            interaction.getConsole().writeLine("`- músculos: " + exercise.getMuscles());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
