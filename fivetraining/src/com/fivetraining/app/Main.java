package com.fivetraining.app;

import com.fivetraining.app.commands.*;
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

        guard.addItem(new ConsoleSeparator("> Painél do instrutor"));
        guard.addItem(new RegisterUserCommand(userDAO));
        guard.addItem(new ConsoleSeparator("> Painél do usuário"));
        guard.addItem(new SignInCommand(userSession, userDAO));
        guard.addItem(new SignOutCommand(userSession));
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
        console.writeLine("   reg-aluno 834.380.650-61 \"Jaime Daniel\" 25/07/2005");
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