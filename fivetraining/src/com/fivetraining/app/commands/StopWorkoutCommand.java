package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.app.models.Workout;
import com.fivetraining.app.models.WorkoutActivity;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class StopWorkoutCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final WorkoutDAO workoutDAO;

    public StopWorkoutCommand(UserSession userSession, WorkoutDAO workoutDAO) {
        this.userSession = userSession;
        this.workoutDAO = workoutDAO;

        addParameter(ConsoleParameter.createDateTime("tempo de encerramento", true));
    }

    @Override
    public String getName() {
        return "enc-treino";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        LocalDateTime endTime = interaction.getArgument("tempo de encerramento").asDateTime();

        try {
            Workout unfinishedWorkout = workoutDAO.findUnfinishedByUserId(userSession.getAuthenticatedUser().getId());

            if (unfinishedWorkout == null) {
                throw new ConsoleCommandExecutionException("Você precisa ter um treino em andamento para encerrá-lo");
            }

            unfinishedWorkout.setEndTime(endTime);
            workoutDAO.update(unfinishedWorkout);

            interaction.getConsole().writeLine("O treino foi encerrado com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
