package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.WorkoutActivityDAO;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.app.models.Workout;
import com.fivetraining.app.models.WorkoutActivity;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class InspectWorkoutCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ExerciseDAO exerciseDAO;
    private final ProgramDAO programDAO;
    private final WorkoutDAO workoutDAO;
    private final WorkoutActivityDAO workoutActivityDAO;

    public InspectWorkoutCommand(UserSession userSession, ExerciseDAO exerciseDAO, ProgramDAO programDAO, WorkoutDAO workoutDAO, WorkoutActivityDAO workoutActivityDAO) {
        this.userSession = userSession;
        this.exerciseDAO = exerciseDAO;
        this.programDAO = programDAO;
        this.workoutDAO = workoutDAO;
        this.workoutActivityDAO = workoutActivityDAO;
    }

    @Override
    public String getName() {
        return "ver-treino";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        try {
            Workout workout = workoutDAO.findUnfinishedByUserId(userSession.getAuthenticatedUser().getId());

            if (workout == null) {
                throw new ConsoleCommandExecutionException("Você precisa ter um treino em andamento para visualizá-lo");
            }

            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  id: " + workout.getId());
            interaction.getConsole().writeLine("|  nome do programa: " + workout.getProgramName());
            interaction.getConsole().writeLine("|  tempo de início: " + workout.getStartTime());
            interaction.getConsole().writeLine("|  atividades:");

            List<WorkoutActivity> activities = workoutActivityDAO.findAllWithWorkoutId(workout.getId());

            for (WorkoutActivity activity : activities) {
                Exercise exercise = exerciseDAO.findByCode(activity.getExerciseCode());

                interaction.getConsole().writeLine("|");
                interaction.getConsole().writeLine("|  o  concluído: " + (activity.isCompleted() ? "[x]" : "[ ]"));
                interaction.getConsole().writeLine("|  |  código: " + exercise.getCode());
                interaction.getConsole().writeLine("|  |  nome: " + exercise.getName());
                interaction.getConsole().writeLine("|  |  músculos: " + exercise.getMuscles());
                interaction.getConsole().writeLine("|  |  carga: " + activity.getLoad());
                interaction.getConsole().writeLine("|  |  n° de séries: " + activity.getSets());
                interaction.getConsole().writeLine("|  |  n° mínimo de repetições: " + activity.getMinimumRepetitions());
                interaction.getConsole().writeLine("|  |  n° máximo de repetições: " + activity.getMaximumRepetitions());
                interaction.getConsole().writeLine("|  `- minutos de descanso: " + activity.getRestingTime());
            }

            interaction.getConsole().writeLine("`-");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
