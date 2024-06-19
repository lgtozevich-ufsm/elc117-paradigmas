import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Q02 {
    public static void main(String[] args) {
        List<Integer> votes = readVotes();

        System.out.println();
        System.out.println("- total de votos para cada candidato:");

        IntStream.rangeClosed(1, 4).forEach(i -> {
            System.out.println("  candidato " + i + ": " + countVotes(votes, i));
        });

        System.out.println("- total de votos nulos: " + countVotes(votes, 5));
        System.out.println("- total de votos em branco: " + countVotes(votes, 6));
    }

    public static List<Integer> readVotes() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> votes = new ArrayList<>();

        System.out.println("Digite os votos: (0 para finalizar)");

        while (true) {
            System.out.print("Voto[" + (votes.size() + 1) + "]: ");

            int vote = scanner.nextInt();

            if (vote <= 0) {
                break;
            }

            if (!isVoteValid(vote)) {
                System.out.println("O código " + vote + " não é um voto válido.");
                continue;
            }

            votes.add(vote);
        }

        scanner.close();

        return votes;
    }

    public static long countVotes(List<Integer> votes, int code) {
        return votes.stream().filter(vote -> vote == code).count();
    }

    public static boolean isVoteValid(int code) {
        return code >= 1 && code <= 6;
    }
}
