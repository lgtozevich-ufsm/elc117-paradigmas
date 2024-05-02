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

        addParameter(ConsoleParameter.createString("codigo exercicio", true));
        addParameter(ConsoleParameter.createString("nome", true));
        addParameter(ConsoleParameter.createString("musculos", true));
    }

    @Override
    public String getName() {
        return "reg-exerc";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String name = interaction.getArgument("nome").asString();
        String muscles = interaction.getArgument("musculos").asString();

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setMuscles(muscles);

        try {
            exerciseDAO.insert(exercise);

            interaction.getConsole().writeLine("O exercício foi registrado com sucesso.");
            interaction.getConsole().writeLine("- Código do exercício: " + exercise.getCode());
            interaction.getConsole().writeLine("- Nome: " + exercise.getName());
            interaction.getConsole().writeLine("- Músculos trabalhados: " + exercise.getMuscles());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException("Um erro ocorreu ao registrar o exercício: " + exception);
        }
    }
}
