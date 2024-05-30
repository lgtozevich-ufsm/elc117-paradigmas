package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.daos.WorkoutActivityDAO;
import com.fivetraining.app.daos.WorkoutDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.app.models.Workout;
import com.fivetraining.app.models.WorkoutActivity;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class UpdateWorkoutActivityCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ExerciseDAO exerciseDAO;
    private final WorkoutDAO workoutDAO;
    private final WorkoutActivityDAO workoutActivityDAO;

    public UpdateWorkoutActivityCommand(UserSession userSession, ExerciseDAO exerciseDAO, WorkoutDAO workoutDAO, WorkoutActivityDAO workoutActivityDAO) {
        this.userSession = userSession;
        this.exerciseDAO = exerciseDAO;
        this.workoutDAO = workoutDAO;
        this.workoutActivityDAO = workoutActivityDAO;

        addParameter(ConsoleParameter.createInteger("código do exercício", true));
        addParameter(ConsoleParameter.createInteger("nova carga", true));
    }

    @Override
    public String getName() {
        return "alt-treino";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        int exerciseCode = interaction.getArgument("código do exercício").asInteger();
        int load = interaction.getArgument("nova carga").asInteger();

        try {
            Workout unfinishedWorkout = workoutDAO.findUnfinishedByUserId(userSession.getAuthenticatedUser().getId());

            if (unfinishedWorkout == null) {
                throw new ConsoleCommandExecutionException("Você precisa ter um treino em andamento para atualizar um exercício");
            }

            WorkoutActivity activity = workoutActivityDAO.findByWorkoutIdAndExerciseCode(unfinishedWorkout.getId(), exerciseCode);

            if (activity == null) {
                throw new ConsoleCommandExecutionException("Nenhuma exercício com o código \"" + exerciseCode + "\" foi encontrado no treino em andamento");
            }

            Exercise exercise = exerciseDAO.findByCode(exerciseCode);

            if (activity.isCompleted()) {
                throw new ConsoleCommandExecutionException("O exercício \"" + exercise.getName() + "\" já foi completado");
            }

            activity.setLoad(load);

            workoutActivityDAO.update(activity);

            interaction.getConsole().writeLine("O exercício \"" + exercise.getName() + "\" foi atualizado com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
