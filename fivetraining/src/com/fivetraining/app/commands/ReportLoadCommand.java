package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.WorkoutActivityDAO;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Workout;
import com.fivetraining.app.models.WorkoutActivity;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ReportLoadCommand extends ConsoleCommand {
    private UserSession userSession;
    private WorkoutDAO workoutDAO;
    private WorkoutActivityDAO workoutActivityDAO;

    public ReportLoadCommand(UserSession userSession, WorkoutDAO workoutDAO, WorkoutActivityDAO workoutActivityDAO) {
        this.userSession = userSession;
        this.workoutDAO = workoutDAO;
        this.workoutActivityDAO = workoutActivityDAO;

        addParameter(ConsoleParameter.createInteger("código do exercício", true));
    }

    @Override
    public String getName() {
        return "relat-carga";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        int exerciseCode = interaction.getArgument("código do exercício").asInteger();

        try {
            List<Workout> workouts = workoutDAO.findAllWithUserId(userSession.getAuthenticatedUser().getId());

            for (Workout workout : workouts) {
                WorkoutActivity activity = workoutActivityDAO.findByWorkoutIdAndExerciseCode(workout.getId(), exerciseCode);

                if (activity == null || !activity.isCompleted()) {
                    continue;
                }

                interaction.getConsole().write("o " + workout.getStartTime() + " - ");
                interaction.getConsole().write(workout.getEndTime() != null ? workout.getEndTime().toString() : "(em andamento)");
                interaction.getConsole().writeLine(": " + activity.getLoad() + " kg");
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
