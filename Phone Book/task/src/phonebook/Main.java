package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        File directory = new File("directory.txt");
        File find = new File("find.txt");

        int counter = 0;
        List<String> directoryNames = new ArrayList<>();
        List<String> findNames = new ArrayList<>();

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

        System.out.println("Start searching...");
        long startTime = System.nanoTime();

        for (String name : findNames) {
            for (String person : directoryNames) {
                if (person.contains(name)) {
                    counter++;
                }
            }
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        long miliSec = TimeUnit.MILLISECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
        long seconds = TimeUnit.SECONDS.convert(miliSec, TimeUnit.MILLISECONDS);
        long minutes = TimeUnit.MINUTES.convert(seconds, TimeUnit.SECONDS);

        System.out.println("Found " + counter + " / " + findNames.size() + " entries.");
        System.out.println("Time taken " + minutes + " min. " + seconds + " sec. " + miliSec + " ms.");
    }
}
