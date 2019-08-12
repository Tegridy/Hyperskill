package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static List<String> directoryNames = new ArrayList<>();
    private static List<String> findNames = new ArrayList<>();

    private static long totalTimeLinear;
    private static long totalTimeBubble;


    public static void main(String[] args) {
        processFiles();
        System.out.println("Start searching (linear search)...");
        System.out.println(linearSearch(false) + "\n");
        System.out.println("Start searching (bubble sort + jump search)...");
        bubbleSort();
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

        int counter = 0;

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
        long totaltimelinear = 0;

        int jumpLength = (int) Math.sqrt(directoryNames.size());

        int counter = 0;

        while (counter != findNames.size()) {

            long startTime = System.nanoTime();

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



            long endTime = 0;
            endTime = System.nanoTime();
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
            totaltimelinear += endTimeLinear - startLinear;

        }

        calculateTime(totalTimeJump);
        calculateTime(totaltimelinear);

        System.out.print("Found " + counter + " / " + findNames.size());
        System.out.println("Time taken " + calculateTime(totalTimeJump + totaltimelinear));
    }

    private static void processFiles(){
        File directory = new File("C:\\Users\\Ja\\Desktop\\files\\directory.txt");
        File find = new File("C:\\Users\\Ja\\Desktop\\files\\find.txt");

        try (Scanner sc = new Scanner(directory)){
            Scanner sc2 = new Scanner(find);

            while (sc.hasNextLine()){
                directoryNames.add(sc.nextLine());
            }

            while (sc2.hasNextLine()){
                findNames.add(sc2.nextLine());
            }
        } catch (FileNotFoundException e){
            System.out.println("Problem with opening file.");
        }
        System.out.println("File reading complete.");
    }

    private static String calculateTime(long totalTime){

        long minutes = TimeUnit.NANOSECONDS.toMinutes(totalTime);
        long seconds =  TimeUnit.NANOSECONDS.toSeconds(totalTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(totalTime));
        long miliSec = TimeUnit.NANOSECONDS.toMillis(totalTime) -
                TimeUnit.SECONDS.toMillis(TimeUnit.NANOSECONDS.toSeconds(totalTime));

        return minutes + " min. " + seconds + " sec. " + miliSec + " ms.";
    }

}
