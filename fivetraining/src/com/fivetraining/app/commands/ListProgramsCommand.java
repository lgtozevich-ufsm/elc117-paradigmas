package com.fivetraining.app.commands;

import com.fivetraining.app.UserSession;
import com.fivetraining.app.daos.ExerciseDAO;
import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Exercise;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListProgramsCommand extends ConsoleCommand {
    private final UserSession userSession;
    private final ExerciseDAO exerciseDAO;
    private final ProgramDAO programDAO;
    private final ProgramExerciseDAO programExerciseDAO;

    public ListProgramsCommand(UserSession userSession, ExerciseDAO exerciseDAO, ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO) {
        this.userSession = userSession;
        this.exerciseDAO = exerciseDAO;
        this.programDAO = programDAO;
        this.programExerciseDAO = programExerciseDAO;
    }

    @Override
    public String getName() {
        return "list-progs";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        userSession.throwIfNotAuthenticated();

        try {
            List<Program> programs = programDAO.findAllWithUserId(userSession.getAuthenticatedUser().getId());

            for (Program program : programs) {
                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o  id: " + program.getId());
                interaction.getConsole().writeLine("|  nome: " + program.getName());
                interaction.getConsole().writeLine("|  exercícios: ");

                List<ProgramExercise> programExercises = programExerciseDAO.findAllWithProgramId(program.getId());

                for (ProgramExercise programExercise : programExercises) {
                    Exercise exercise = exerciseDAO.findByCode(programExercise.getExerciseCode());

                    interaction.getConsole().writeLine("|    o  código: " + exercise.getCode());
                    interaction.getConsole().writeLine("|    |  nome: " + exercise.getName());
                    interaction.getConsole().writeLine("|    |  músculos: " + exercise.getMuscles());
                    interaction.getConsole().writeLine("|    |  carga: " + programExercise.getLoad());
                    interaction.getConsole().writeLine("|    |  n° de séries: " + programExercise.getSets());
                    interaction.getConsole().writeLine("|    |  n° mínimo de repetições: " + programExercise.getMinimumRepetitions());
                    interaction.getConsole().writeLine("|    |  n° máximo de repetições: " + programExercise.getMaximumRepetitions());
                    interaction.getConsole().writeLine("|    `- minutos de descanso: " + programExercise.getRestingTime());
                    interaction.getConsole().writeLine("|");
                }

                interaction.getConsole().writeLine("`-");
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
