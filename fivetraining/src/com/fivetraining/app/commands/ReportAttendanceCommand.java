package com.fivetraining.app.commands;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Workout;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

public class ReportAttendanceCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final WorkoutDAO workoutDAO;

    public ReportAttendanceCommand(UserSession userSession, WorkoutDAO workoutDAO) {
        this.userSession = userSession;
        this.workoutDAO = workoutDAO;

        addParameter(ConsoleParameter.createDateTime("tempo inicial", true));
        addParameter(ConsoleParameter.createDateTime("tempo final", true));
    }


    @Override
    public String getName() {
        return "relat-presenca";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        LocalDateTime fromTime = interaction.getArgument("tempo inicial").asDateTime();
        LocalDateTime toTime = interaction.getArgument("tempo final").asDateTime();
        
        try {
            List<Workout> workouts = workoutDAO.findAllWithUserIdInRange(userSession.getAuthenticatedUser().getId(), fromTime, toTime);

            for (Workout workout : workouts) {
                interaction.getConsole().write("o " + workout.getStartTime() + " - ");
                interaction.getConsole().write(workout.getEndTime() != null ? workout.getEndTime().toString() : "(em andamento)");
                interaction.getConsole().writeLine(": " + workout.getProgramName());
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
