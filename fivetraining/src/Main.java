import commands.CreateUserCommand;
import commands.ExitCommand;
import commands.HelpCommand;
import console.Console;
import console.ConsoleGuard;
import console.items.ConsoleSeparator;

public class Main {
    public static void main(String[] args) {
        Console console = new Console(System.in, System.out);
        ConsoleGuard guard = new ConsoleGuard(console);

        guard.addItem(new ConsoleSeparator("> Usuário"));
        guard.addItem(new CreateUserCommand());
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
        console.writeLine("   criarusuario \"Jaime Daniel\" 18 true");
        console.writeLine();
        console.writeLine();


        while (true) {
            console.write("$ ");
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