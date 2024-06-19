import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;

record Sample(double salary, int children) {}

public class Q01 {
    public static void main(String[] args) {
        List<Sample> samples = survey();

        System.out.println();
        System.out.println("a) média de salário da população: " + computeAverageSalary(samples).orElse(0.0));
        System.out.println("b) média de número de filhos: " + computeAverageChildren(samples).orElse(0.0));
        System.out.println("c) maior salário: " + findMaxSalary(samples).orElse(0.0));
        System.out.println("d) percentual de pessoas com salário de até R$ 1000,00: " + findRatioSalaryUpTo1000(samples) * 100.0);
    }

    private static List<Sample> survey()
    {
        Scanner scanner = new Scanner(System.in);
        List<Sample> samples = new ArrayList<>();

        System.out.println("Digite as suas amostras: (-1 para finalizar)");

        while (true) {
            System.out.println("Amostra[" + (samples.size() + 1) + "]:");

            System.out.print("- Salário: ");
            double salary = scanner.nextDouble();

            if (salary < 0.0) {
                break;
            }

            System.out.print("- Número de filhos: ");
            int children = scanner.nextInt();

            if (children < 0) {
                break;
            }

            samples.add(new Sample(salary, children));
        }

        scanner.close();

        return samples;
    }

    private static OptionalDouble computeAverageSalary(List<Sample> samples) {
        return samples.stream().mapToDouble(Sample::salary).average();
    }

    private static OptionalDouble computeAverageChildren(List<Sample> samples) {
        return samples.stream().mapToInt(Sample::children).average();
    }

    private static OptionalDouble findMaxSalary(List<Sample> samples) {
        return samples.stream().mapToDouble(Sample::salary).max();
    }

    private static double findRatioSalaryUpTo1000(List<Sample> samples) {
        return (double)samples.stream().filter(sample -> sample.salary() <= 1000).count() / samples.size();
    }
}
