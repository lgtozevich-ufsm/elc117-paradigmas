package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListExercisesCommand extends ConsoleCommand {
    private final ExerciseDAO exerciseDAO;

    public ListExercisesCommand(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
    }

    @Override
    public String getName() {
        return "list-exercs";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        try {
            List<Exercise> exercises = exerciseDAO.findAll();

            for (Exercise exercise : exercises) {
                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o  código: " + exercise.getCode());
                interaction.getConsole().writeLine("|  nome: " + exercise.getName());
                interaction.getConsole().writeLine("`- músculos: " + exercise.getMuscles());
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
