package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    static List<String> directoryNames = new ArrayList<>();
    private static List<String> findNames = new ArrayList<>();

    private static long totalTimeLinear;
    private static long totalTimeBubble;
    private static long totalTimeBinary;

    private static int counter;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        processFiles();

        System.out.println("Choose (0 - linear search, 1 - bubble sort + jump search, 2 - quick sort + binary search): ");
        switch (sc.nextInt()){
            case 0:
                System.out.println("Start searching (linear search)...");
                System.out.println(linearSearch(false));
                break;
            case 1:
                System.out.println("Start searching (bubble sort + jump search)...");
                bubbleSort();
                break;
            case 2:
                System.out.println("Start searching (quick sort + binary search)...");

                long startTime = System.nanoTime();
                quickSort(0, directoryNames.size()-1);

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;

                System.out.println("Sorting time: " + calculateTime(totalTime));
                System.out.println(binarySearch());

                long totalTimeQS = totalTime + totalTimeBinary;

                System.out.println("Found " + counter + " / " + findNames.size() + " entries. " +
                        "Time taken " + calculateTime(totalTimeQS));

                break;
            default:
                System.out.println("Wrong choice.");
                break;
        }


    }

    private static void bubbleSort(){
        // Sorting dictionary by first name

        boolean breakpoint = false;
        long startTime = System.nanoTime();

        for(int i = 0; i < directoryNames.size(); i++){
            for (int j = 0; j < directoryNames.size() - 1; j++) {
                String[] firstPerson = directoryNames.get(j).split(" ");
                String[] secondPerson = directoryNames.get(j + 1).split(" ");

                if (firstPerson[1].compareTo(secondPerson[1]) > 0) {
                    String swap = directoryNames.get(j);
                    directoryNames.set(j, directoryNames.get(j + 1));
                    directoryNames.set(j + 1, swap);
                    }

                }
            // Breaking loop if sorting is taking too long
            if (totalTimeBubble > (totalTimeLinear*10)) {

                System.out.print( "Sorting time: " + calculateTime(totalTimeBubble));
                System.out.println(" - STOPPED, moved to linear search ");

                System.out.println(linearSearch(true));
                breakpoint = true;
                break;
            }

            long endTime = System.nanoTime();
            totalTimeBubble = endTime - startTime;
            }
        if (!breakpoint)     jumpSearch();
    }

    private static String linearSearch(boolean fromBubble){

        long startTime = System.nanoTime();

        counter = 0;

        for (String name : findNames) {
            for (String person : directoryNames) {
                if (person.contains(name)) {
                    counter++;
                }
            }
        }

        long endTime = System.nanoTime();
        totalTimeLinear = endTime - startTime;


        if (fromBubble){
            System.out.println("Searching time: " + calculateTime(totalTimeLinear));
            totalTimeLinear += totalTimeBubble;
        }

        return "Found " + counter + " / " + findNames.size() + " entries. " +
                "Time taken " + calculateTime(totalTimeLinear);
    }

    private static void jumpSearch(){

        int currentRight = 0;
        int prevRight = 0;

        long totalTimeJump = 0;
        long linearTime = 0;

        int jumpLength = (int) Math.sqrt(directoryNames.size());

        counter = 0;

        long startTime = System.nanoTime();

        while (counter != findNames.size()) {

            while (currentRight < directoryNames.size() - 1) {

                currentRight = Math.min(directoryNames.size(), currentRight + jumpLength);

                StringBuilder block = new StringBuilder();

                for (int i = prevRight; i < currentRight; i++) {
                    block.append(directoryNames.get(i));
                }

                if (block.toString().contains(directoryNames.get(currentRight))) {
                    break;
                }

                prevRight = currentRight;

            }

            long endTime = System.nanoTime();
            totalTimeJump += endTime - startTime;

            // Linear

            long startLinear = System.nanoTime();

            for (int i = currentRight; i > prevRight; i--) {
                for (int j = 0; j < findNames.size(); j++) {
                    if (findNames.get(j).contains(directoryNames.get(i))){
                        counter++;
                    }
                }
            }

            long endTimeLinear = System.nanoTime();
            linearTime += endTimeLinear - startLinear;

        }

        System.out.print("Found " + counter + " / " + findNames.size());
        System.out.println("Time taken " + calculateTime(totalTimeJump + linearTime));
    }

    private static void quickSort(int left, int right){
        if(left < right){
            int pivotIndex = partition(left, right);
            quickSort(left, pivotIndex-1);
            quickSort(pivotIndex+1, right);
        }
    }

    private static int partition(int left, int right){
        String[] info = directoryNames.get(right).split(" ");
        String pivot = info[1];
        int partitionIndex = left;

        for (int i = left; i < right; i++) {
            String[] person = directoryNames.get(i).split(" ");
            if (person[1].compareTo(pivot) < 0){
                swap(i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(partitionIndex, right);

        return partitionIndex;
    }

    private static void swap(int i, int j){
        String temp = directoryNames.get(i);
        directoryNames.set(i, directoryNames.get(j));
        directoryNames.set(j, temp);
    }

    private static String binarySearch() {

        long startTime = System.nanoTime();

        counter = 0;
        for (String name : findNames) {

            String[] info = name.split(" ");
            String personToFind = info[0];

            int left = 0;
            int right = directoryNames.size() - 1;

            while (left <= right) {

                int mid = left + (right - left) / 2;

                String[] info2 = directoryNames.get(mid).split(" ");
                String personFromDictionary = info2[1];

                if (personToFind.equals(personFromDictionary)) {
                    counter++;
                    break;
                } else if (personToFind.compareTo(personFromDictionary) < 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        long endTime = System.nanoTime();
        totalTimeBinary = endTime - startTime;

        return "Searching time: " + calculateTime(totalTimeBinary);
    }

    private static void processFiles(){
        File directory = new File("C:\\IdeaProjects\\Phone Book\\Phone Book\\task\\src\\phonebook\\dictionary.txt");
        File find = new File("C:\\IdeaProjects\\Phone Book\\Phone Book\\task\\src\\phonebook\\find.txt");

        try (Scanner sc = new Scanner(directory)){
            Scanner sc2 = new Scanner(find);

            while (sc.hasNextLine()){
                directoryNames.add(sc.nextLine());
            }

            while (sc2.hasNextLine()){
                findNames.add(sc2.nextLine());
            }

            System.out.println("File reading complete.");

        } catch (FileNotFoundException e){
            System.out.println("Problem with opening file.");
        }
    }

    private static String calculateTime(long totalTime){

        long minutes = TimeUnit.NANOSECONDS.toMinutes(totalTime);
        long seconds =  TimeUnit.NANOSECONDS.toSeconds(totalTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(totalTime));
        long milSec = TimeUnit.NANOSECONDS.toMillis(totalTime) -
                TimeUnit.SECONDS.toMillis(TimeUnit.NANOSECONDS.toSeconds(totalTime));

        return minutes + " min. " + seconds + " sec. " + milSec + " ms.";
    }

}
