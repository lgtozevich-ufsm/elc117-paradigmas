import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Q04 {
    public static void main(String[] args) {
        List<Integer> numbers = readNumbers();

        System.out.println();
        System.out.println("[ 0,  25]: " + countNumbersInClosedRange(numbers, 0, 25));
        System.out.println("[26,  50]: " + countNumbersInClosedRange(numbers, 26, 50));
        System.out.println("[51,  75]: " + countNumbersInClosedRange(numbers, 51, 75));
        System.out.println("[76, 100]: " + countNumbersInClosedRange(numbers, 76, 100));
    }

    private static List<Integer> readNumbers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite 10 números:");

        List<Integer> numbers = IntStream.rangeClosed(1, 10)
                .map(i -> {
                    System.out.print("Número[" + i + "]: ");
                    return scanner.nextInt();
                })
                .boxed()
                .toList();

        scanner.close();

        return numbers;
    }

    private static long countNumbersInClosedRange(List<Integer> numbers, int start, int end) {
        return numbers.stream().filter(n -> n >= start && n <= end).count();
    }
}
