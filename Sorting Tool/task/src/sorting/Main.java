package sorting;

import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Long> numbers = new ArrayList<>();

        while (scanner.hasNextLong()) {
            try {
                numbers.add(scanner.nextLong());
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                break;
            }
        }

        System.out.println("Total numbers: " + numbers.size());
        Predicate<Long> predicate = x -> x >= Collections.max(numbers);
        long maxElement = numbers.stream().filter(predicate).count();
        System.out.println("The greatest number: " + Collections.max(numbers) + " (" + maxElement + " time(s)).");

    }
}
