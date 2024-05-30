package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.daos.WorkoutActivityDAO;
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
import java.util.List;

public class StartWorkoutCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ProgramDAO programDAO;
    private final ProgramExerciseDAO programExerciseDAO;
    private final WorkoutDAO workoutDAO;
    private final WorkoutActivityDAO workoutActivityDAO;

    public StartWorkoutCommand(UserSession userSession, ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO, WorkoutDAO workoutDAO, WorkoutActivityDAO workoutActivityDAO) {
        this.userSession = userSession;
        this.programDAO = programDAO;
        this.programExerciseDAO = programExerciseDAO;
        this.workoutDAO = workoutDAO;
        this.workoutActivityDAO = workoutActivityDAO;

        addParameter(ConsoleParameter.createInteger("id do programa", true));
        addParameter(ConsoleParameter.createDateTime("tempo de início", true));
    }

    @Override
    public String getName() {
        return "inic-treino";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        int programId = interaction.getArgument("id do programa").asInteger();
        LocalDateTime startDate = interaction.getArgument("tempo de início").asDateTime();

        try {
            Program program = programDAO.findById(programId);

            if (program == null) {
                throw new ConsoleCommandExecutionException("Nenhum programa com o id \"" + programId + "\" foi encontrado");
            }

            if (program.getUserId() != userSession.getAuthenticatedUser().getId()) {
                throw new ConsoleCommandExecutionException("Você não tem permissão para iniciar um treino com este programa.");
            }

            Workout unfinishedWorkout = workoutDAO.findUnfinishedByUserId(userSession.getAuthenticatedUser().getId());

            if (unfinishedWorkout != null) {
                throw new ConsoleCommandExecutionException("Você precisa encerrar o treino anterior antes de começar um novo.");
            }

            List<ProgramExercise> programExercise = programExerciseDAO.findAllWithProgramId(programId);

            Workout workout = new Workout();
            workout.setUserId(userSession.getAuthenticatedUser().getId());
            workout.setProgramName(program.getName());
            workout.setStartTime(startDate);

            workoutDAO.insert(workout);

            for (ProgramExercise exercise : programExercise) {
                WorkoutActivity activity = new WorkoutActivity();
                activity.setWorkoutId(workout.getId());
                activity.setExerciseCode(exercise.getExerciseCode());
                activity.setLoad(exercise.getLoad());
                activity.setSets(exercise.getSets());
                activity.setMinimumRepetitions(exercise.getMinimumRepetitions());
                activity.setMaximumRepetitions(exercise.getMaximumRepetitions());
                activity.setRestingTime(exercise.getRestingTime());

                workoutActivityDAO.insert(activity);
            }

            interaction.getConsole().writeLine("O treino do programa \"" + program.getName() + "\" foi iniciado com sucesso.");
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
