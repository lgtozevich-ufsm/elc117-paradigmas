package com.fivetraining.app;

import com.fivetraining.app.commands.*;
import com.fivetraining.app.daos.*;
import com.fivetraining.app.daos.Database;
import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.console.Console;
import com.fivetraining.console.ConsoleGuard;
import com.fivetraining.console.items.ConsoleSeparator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:fivetraining.sqlite");

        Console console = new Console(System.in, System.out);
        ConsoleGuard guard = new ConsoleGuard(console);

        UserSession userSession = new UserSession();

        Database database = new Database(connection);
        UserDAO userDAO = new UserDAO(database);
        PlanDAO planDAO = new PlanDAO(database);
        ExerciseDAO exerciseDAO = new ExerciseDAO(database);
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO(database);
        ProgramDAO programDAO = new ProgramDAO(database);
        ProgramExerciseDAO programExerciseDAO = new ProgramExerciseDAO(database);
        WorkoutDAO workoutDAO = new WorkoutDAO(database);
        WorkoutActivityDAO workoutActivityDAO = new WorkoutActivityDAO(database);

        guard.addItem(new ConsoleSeparator("> Painél do instrutor"));
        guard.addItem(new RegisterPlanCommand(planDAO));
        guard.addItem(new RegisterExerciseCommand(exerciseDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new RegisterUserCommand(userDAO));
        guard.addItem(new UpdateUserCommand(userDAO));
        guard.addItem(new DeleteUserCommand(userDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new SearchUserByCPFCommand(userDAO));
        guard.addItem(new SearchUserByNameCommand(userDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new SubscribeCommand(userDAO, planDAO, subscriptionDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new RegisterProgramCommand(userDAO, programDAO));
        guard.addItem(new DeleteProgramCommand(programDAO, programExerciseDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new AddProgramExerciseCommand(exerciseDAO, programDAO, programExerciseDAO));
        guard.addItem(new UpdateProgramExerciseCommand(exerciseDAO, programExerciseDAO));
        guard.addItem(new RemoveProgramExerciseCommand(exerciseDAO, programExerciseDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new ListUsersCommand(userDAO));
        guard.addItem(new ListExercisesCommand(exerciseDAO));
        guard.addItem(new ListPlansCommand(planDAO));
        guard.addItem(new ListSubscriptionsCommand(subscriptionDAO));
        
        guard.addItem(new ConsoleSeparator("> Painél do usuário"));
        guard.addItem(new SignInCommand(userSession, userDAO));
        guard.addItem(new SignOutCommand(userSession));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new StartWorkoutCommand(userSession, programDAO, programExerciseDAO, workoutDAO, workoutActivityDAO));
        guard.addItem(new InspectWorkoutCommand(userSession, exerciseDAO, programDAO, workoutDAO, workoutActivityDAO));
        guard.addItem(new UpdateWorkoutActivityCommand(userSession, exerciseDAO, workoutDAO, workoutActivityDAO));
        guard.addItem(new CompleteWorkoutActivityCommand(userSession, exerciseDAO, workoutDAO, workoutActivityDAO));
        guard.addItem(new StopWorkoutCommand(userSession, workoutDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new ListProgramsCommand(userSession, exerciseDAO, programDAO, programExerciseDAO));
        guard.addItem(new ListWorkoutsCommand(userSession, exerciseDAO, workoutDAO, workoutActivityDAO));
        guard.addItem(new ConsoleSeparator());
        guard.addItem(new ReportAttendanceCommand(userSession, workoutDAO));
        guard.addItem(new ReportLoadCommand(userSession, workoutDAO, workoutActivityDAO));

        guard.addItem(new ConsoleSeparator("> Outros"));
        guard.addItem(new HelpCommand(guard));
        guard.addItem(new ExitCommand());

        console.writeLine("Bem-vindo à Five Training.");
        console.writeLine("Exercite seus músculos à medida que você programa em bash.");
        console.writeLine();
        console.writeLine(" * Digite \"help\" para ver a lista de comandos disponíveis.");
        console.writeLine(" * Utilize \"\" para digitar textos ou preserve literais com \\.");
        console.writeLine(" * Parâmetros <> são obrigatórios e [] são opcionais.");
        console.writeLine();
        console.writeLine("Exemplo:");
        console.writeLine();
        console.writeLine("   reg-aluno 834.380.650-61 \"Jaime Daniel\" 2005-07-25");
        console.writeLine();
        console.writeLine();

        while (true) {
            console.write("$ ");

            if (userSession.isAuthenticated()) {
                console.write("(" + userSession.getAuthenticatedUser().getName() + ") ");
            }

            String line = console.readLine();

            if (line.isBlank()) {
                continue;
            }

            try {
                guard.execute(line);
            } catch (Exception e) {
                console.writeLine("ERROR: " + e.getMessage());
            }

            console.writeLine();
        }
    }
}