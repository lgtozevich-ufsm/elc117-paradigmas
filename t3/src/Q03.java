import java.util.*;

public class Q03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 5; i++) {
            System.out.println("Aluno " + i + ":");

            System.out.print("- Código: ");
            int code = scanner.nextInt();
            ArrayList<Double> grades = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                System.out.print("- Nota " + (j + 1) + ": ");
                grades.add(scanner.nextDouble());
            }

            System.out.println("Código: " + code);
            grades.forEach(grade -> System.out.println("Nota: " + grade));

            double averageGrade = calcAverageGrade(grades);

            System.out.println("Média: " + averageGrade);
            System.out.println(averageGrade >= 5 ? "Aprovado" : "Reprovado");
        }
    }

    private static double calcAverageGrade(ArrayList<Double> grades) {
        double maxGrade = grades.stream().max(Double::compareTo).orElse(0.0);
        grades.remove(maxGrade);

        double averageGrade = grades.stream().mapToDouble(Double::doubleValue).sum();

        return (maxGrade * 4 + averageGrade * 3) / 10;
    }
}