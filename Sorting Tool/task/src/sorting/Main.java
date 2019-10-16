package sorting;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean sortingType = false; // false for natural, true = byCount

        if (args[0].equals("-dataType")){
            if(args.length > 2 && (args[2].equals("-sortingType") && args[3].equals("byCount"))){
                sortingType = true;
            }
            switch (args[1]){
                case "long":
                    readLong(sortingType);
                    break;
                case "line":
                    readLine(sortingType);
                    break;
                case "word":
                    readWord(sortingType);
                    break;
                default:
                    System.out.println("Wrong command!");
                    break;
            }
        }


    }

    public static void readLong(boolean sortingType){
        List<Long> numbers = new ArrayList<>();


        while (scanner.hasNextLong()) {
            try {
                numbers.add(scanner.nextLong());
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                break;
            }
        }

        if (sortingType){
            System.out.println("Total numbers: " + numbers.size());

            Map<Long, Long> dataEntryByCount = numbers.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Map<Long, Long> result = new LinkedHashMap<>();
            dataEntryByCount.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

            for (Map.Entry e : result.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue() +" time(s), " + ((long)e.getValue() * 100) / numbers.size() + "%");
            }

        } else {

            long max;
            long maxElements;
            int percentage;

            System.out.println("Total numbers: " + numbers.size());
            if (numbers.size() > 0) {
                Predicate<Long> predicate = x -> x >= Collections.max(numbers);
                max = numbers.stream().max(Long::compare).get();
                maxElements = numbers.stream().filter(predicate).count();
                percentage = (int) (maxElements * 100) / numbers.size();

                System.out.println("The greatest number: " + max + " (" + maxElements + " time(s) " + percentage + "%).");
            }
        }
    }

    public static void readLine(boolean sortingType) {
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            try {
                lines.add(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        if (sortingType) {
            Map<String, Long> dataEntryByCount = lines.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Map<String, Long> result = new LinkedHashMap<>();
            dataEntryByCount.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

            for (Map.Entry e : result.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue() + " time(s), " + ((long) e.getValue() * 100) / lines.size() + "%");
            }
        } else {

            String max;
            long maxElements;
            int percentage;

            System.out.println("Total lines: " + lines.size());
            if (lines.size() > 0) {
                max = Collections.max(lines, Comparator.comparing(String::length));
                String finalMax = max;
                Predicate<String> predicate = x -> x.equals(finalMax);
                maxElements = lines.stream().filter(predicate).count();
                percentage = (int) (maxElements * 100) / lines.size();

                System.out.println("The longest line: \n" + max + "\n (" + maxElements + " time(s) " + percentage + "%).");
            }
        }
    }

    private static void readWord(boolean sortingType) {
        List<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            try {
                words.add(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        String max;
        long maxElements;
        int percentage;

        System.out.println("Total words: " + words.size());

        if (sortingType) {
            Map<String, Long> dataEntryByCount = words.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Map<String, Long> result = new LinkedHashMap<>();
            dataEntryByCount.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

            for (Map.Entry e : result.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue() + " time(s), " + ((long) e.getValue() * 100) / words.size() + "%");
            }
        } else {

            if (words.size() > 0) {
                max = Collections.max(words, Comparator.comparing(String::length));
                String finalMax = max;
                Predicate<String> predicate = x -> x.equals(finalMax);
                maxElements = words.stream().filter(predicate).count();
                percentage = (int) (maxElements * 100) / words.size();

                System.out.println("The longest word: " + max + " (" + maxElements + " time(s) " + percentage + "%).");
            }
        }
    }

}
