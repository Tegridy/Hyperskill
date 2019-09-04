package analyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {


        byte[] signature = args[2].getBytes();

        long startTime, endTime, totalTime;

        try {
            byte[] allBytes = Files.readAllBytes(Path.of(args[1]));

            if (args[0].equals("--naive")){
                startTime = System.nanoTime();
                System.out.println(naive(allBytes, signature) == 1 ? args[3] : "Unknown file type");
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.println("It took: " + (double) TimeUnit.NANOSECONDS.toSeconds(totalTime) + " seconds");
            } else if (args[0].equals("--KMP")){
                startTime = System.nanoTime();
                System.out.println(indexOf(allBytes, signature) == 1 ? args[3] : "Unknown file type");
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.println("It took: " + (double) TimeUnit.NANOSECONDS.toSeconds(totalTime) + " seconds");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    static int naive(byte[] allBytes, byte[] fileType){

        for(int i = 0; i < allBytes.length - fileType.length+1; ++i) {
            boolean found = true;
            for(int j = 0; j < fileType.length; ++j) {
                if (allBytes[i+j] != fileType[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return 1;
        }

        return -1;
    }


    private static int indexOf(byte[] data, byte[] pattern) {
        if (data.length == 0) return -1;

        int[] failure = KMPsearch(pattern);
        int j = 0;

        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) { j++; }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }

    private static int[] KMPsearch(byte[] pattern) {
        int[] failure = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }

        return failure;
    }


}
