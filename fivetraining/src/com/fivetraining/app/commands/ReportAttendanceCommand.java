package com.fivetraining.app.commands;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Workout;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

public class ReportAttendanceCommand extends ConsoleCommand {
    private UserSession userSession;
    private WorkoutDAO workoutDAO;

    public ReportAttendanceCommand(UserSession userSession, WorkoutDAO workoutDAO) {
        this.userSession = userSession;
        this.workoutDAO = workoutDAO;
    }


    @Override
    public String getName() {
        return "relat-presenca";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();
        
        try {
            List<Workout> workouts = workoutDAO.findAllWithUserId(userSession.getAuthenticatedUser().getId());

            for (int i = 0; i < workouts.size(); i++) {
                Workout startWorkout = workouts.get(i);

                LocalDate rangeStartDate = startWorkout.getStartTime().toLocalDate();
                LocalDate rangeEndDate = rangeStartDate;

                while ((i + 1) < workouts.size()) {
                    Workout endWorkout = workouts.get(i + 1);

                    if (endWorkout.getStartTime().toLocalDate().isAfter(rangeEndDate.plusDays(1))) {
                        break;
                    }

                    rangeEndDate = endWorkout.getStartTime().toLocalDate();
                    i++;
                }

                long days = Duration.between(rangeStartDate.atStartOfDay(), rangeEndDate.atStartOfDay()).toDays();

                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o " + rangeStartDate);

                if (!rangeStartDate.equals(rangeEndDate)) {
                    for (int j = 0; j < days; j++) {
                        interaction.getConsole().writeLine("| ");
                    }

                    interaction.getConsole().writeLine("o " + rangeEndDate);
                }
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
