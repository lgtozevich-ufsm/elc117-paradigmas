import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Q03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IntStream.rangeClosed(1,5).forEach(i -> {
            System.out.println("Aluno " + i + ":");

            System.out.print("- Código: ");
            int code = scanner.nextInt();

            List<Double> grades = IntStream.rangeClosed(1, 3)
                .mapToDouble(j -> {
                    System.out.print("- Nota " + j + ": ");
                    return scanner.nextDouble();
                })
                .boxed()
                .collect(Collectors.toList());


            double averageGrade = computeAverageGrade(grades);

            System.out.print("O aluno " + code + " com as notas ");
            System.out.print(grades.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
            System.out.print(" foi ");
            System.out.print(averageGrade >= 5 ? "APROVADO" : "REPROVADO");
            System.out.println("com média " + averageGrade + ".");
        });

        scanner.close();
    }

    private static double computeAverageGrade(List<Double> grades) {
        double maximumGrade = grades.stream().max(Double::compareTo).orElse(0.0);
        double remainingGrade =  grades.stream().mapToDouble(Double::doubleValue).sum() - maximumGrade;

        return (maximumGrade * 4 + remainingGrade * 3) / 10;
    }
}
