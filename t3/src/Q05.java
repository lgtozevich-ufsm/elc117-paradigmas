import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;

public class Q05 {
    public static void main(String[] args) {
        List<Double> adjustedPrices = readAdjustedPrices();

        System.out.println();
        System.out.println("A média dos preços atualizados é R$ " + computeAdjustedPriceAverage(adjustedPrices).orElse(0.0) + ".");
    }

    private static List<Double> readAdjustedPrices() {
        Scanner scanner = new Scanner(System.in);
        List<Double> adjustedPrices = new ArrayList<>();

        System.out.println("Digite os produtos: (-1 para finalizar)");

        while (true) {
            System.out.println("Produto " + (adjustedPrices.size() + 1));

            System.out.print("- Código: ");
            int code = scanner.nextInt();

            if (code < 0) {
                break;
            }

            System.out.print("- Preço: ");
            double price = scanner.nextDouble();

            if (price < 0.0) {
                break;
            }

            double adjustedPrice = price * 1.2;

            System.out.println();
            System.out.println("O novo preço do produto " + code + " é R$ " + adjustedPrice);
            System.out.println();

            adjustedPrices.add(adjustedPrice);
        }

        scanner.close();

        return adjustedPrices;
    }

    private static OptionalDouble computeAdjustedPriceAverage(List<Double> adjustedPrices) {
        return adjustedPrices.stream().mapToDouble(Double::valueOf).average();
    }
}
