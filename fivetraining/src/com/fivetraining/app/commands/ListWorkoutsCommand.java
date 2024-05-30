package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.*;
import com.fivetraining.app.models.*;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListWorkoutsCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ExerciseDAO exerciseDAO;
    private final WorkoutDAO workoutDAO;
    private final WorkoutActivityDAO workoutActivityDAO;

    public ListWorkoutsCommand(UserSession userSession, ExerciseDAO exerciseDAO, WorkoutDAO workoutDAO, WorkoutActivityDAO workoutActivityDAO) {
        this.userSession = userSession;
        this.exerciseDAO = exerciseDAO;
        this.workoutDAO = workoutDAO;
        this.workoutActivityDAO = workoutActivityDAO;
    }

    @Override
    public String getName() {
        return "list-treinos";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        try {
            List<Workout> workouts = workoutDAO.findAllWithUserId(userSession.getAuthenticatedUser().getId());

            for (Workout workout : workouts) {
                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o  id: " + workout.getId());
                interaction.getConsole().writeLine("|  nome do programa: " + workout.getProgramName());
                interaction.getConsole().writeLine("|  tempo de início: " + workout.getStartTime());

                if (!workout.hasEnded()) {
                    interaction.getConsole().writeLine("|  tempo de encerramento: (em andamento)");
                } else {
                    interaction.getConsole().writeLine("|  tempo de encerramento: " + workout.getEndTime());
                }

                interaction.getConsole().writeLine("|  atividades:");

                List<WorkoutActivity> activities = workoutActivityDAO.findAllWithWorkoutId(workout.getId());

                for (WorkoutActivity activity : activities) {
                    Exercise exercise = exerciseDAO.findByCode(activity.getExerciseCode());

                    interaction.getConsole().writeLine("|    o  concluído: " + (activity.isCompleted() ? "[x]" : "[ ]"));
                    interaction.getConsole().writeLine("|    |  código: " + exercise.getCode());
                    interaction.getConsole().writeLine("|    |  nome: " + exercise.getName());
                    interaction.getConsole().writeLine("|    |  músculos: " + exercise.getMuscles());
                    interaction.getConsole().writeLine("|    |  carga: " + activity.getLoad());
                    interaction.getConsole().writeLine("|    |  n° de séries: " + activity.getSets());
                    interaction.getConsole().writeLine("|    |  n° mínimo de repetições: " + activity.getMinimumRepetitions());
                    interaction.getConsole().writeLine("|    |  n° máximo de repetições: " + activity.getMaximumRepetitions());
                    interaction.getConsole().writeLine("|    `- minutos de descanso: " + activity.getRestingTime());
                    interaction.getConsole().writeLine("|");
                }

                interaction.getConsole().writeLine("`-");
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
