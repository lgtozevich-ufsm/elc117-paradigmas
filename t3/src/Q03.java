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
            List<Double> grades = IntStream.rangeClosed(1, 3).mapToDouble(j -> {
                System.out.print("- Nota " + (j) + ": ");
                return scanner.nextDouble();
            }).boxed().collect(Collectors.toList());


            System.out.println("Código: " + code);
            grades.forEach(grade -> System.out.println("Nota: " + grade));

            double averageGrade = computeAverageGrade(grades);

            System.out.println("Média: " + averageGrade);
            System.out.println(averageGrade >= 5 ? "Aprovado" : "Reprovado");
        });
    }

    private static double computeAverageGrade(List<Double> grades) {
        double maxGrade = grades.stream().max(Double::compareTo).orElse(0.0);

        double averageGrade =  grades.stream().mapToDouble(Double::doubleValue).sum() - maxGrade;

        return (maxGrade * 4 + averageGrade * 3) / 10;
    }
}
