package sorting;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (args[0].equals("-dataType")){
            switch (args[1].toLowerCase()){
                case "long":
                    readLong();
                    break;
                case "line":
                    readLine();
                    break;
                case "word":
                    readWord();
                    break;
                default:
                    System.out.println("Wrong argument.");
                    break;
            }
        }


    }

    public static void readLong(){
        List<Long> numbers = new ArrayList<>();

        while (scanner.hasNextLong()) {
            try {
                numbers.add(scanner.nextLong());
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                break;
            }
        }

        long max = 0;
        long maxElements = 0;
        int percentage = 0;

        System.out.println("Total numbers: " + numbers.size());
        if (numbers.size() > 0) {
            Predicate<Long> predicate = x -> x >= Collections.max(numbers);
             max = numbers.stream().max(Long::compare).get();
             maxElements = numbers.stream().filter(predicate).count();
             percentage = (int) (maxElements*100) / numbers.size();
        }

        System.out.println("The greatest number: " + max + " (" + maxElements + " time(s) " + percentage + "%).");
    }

    public static void readLine(){
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            try {
                lines.add(scanner.nextLine());
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                break;
            }
        }

        String max = "";
        long maxElements = 0;
        int percentage = 0;

        System.out.println("Total lines: " + lines.size());
        if (lines.size() > 0) {
            max = Collections.max(lines, Comparator.comparing(String::length));
            String finalMax = max;
            Predicate<String> predicate = x -> x.equals(finalMax);
            maxElements = lines.stream().filter(predicate).count();
            percentage = (int) (maxElements*100) / lines.size();
        }
        System.out.println("The longest line: \n" + max + "\n (" + maxElements + " time(s) " + percentage + "%).");
    }

    private static void readWord(){
        List<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            try {
                words.add(scanner.next());
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                break;
            }
        }

        String max = "";
        long maxElements = 0;
        int percentage = 0;

        System.out.println("Total words: " + words.size());
        if (words.size() > 0) {
            max = Collections.max(words, Comparator.comparing(String::length));
            String finalMax = max;
            Predicate<String> predicate = x -> x.equals(finalMax);
            maxElements = words.stream().filter(predicate).count();
            percentage = (int) (maxElements*100) / words.size();
        }
        System.out.println("The longest word: " + max + " (" + maxElements + " time(s) " + percentage + "%).");
    }

}
