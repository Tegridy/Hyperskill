package analyzer;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    public static void main(String[] args) {
        //int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(4);



        byte[] signature = args[0].getBytes();

        try {

            String folderPath = args[2];

            final File folder = new File(folderPath);

            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile()) {

                    byte[] allBytes = Files.readAllBytes(fileEntry.toPath());

                    Future<Integer> f = executor.submit(() -> indexOf(allBytes, signature));

                    System.out.println(fileEntry.getName() + ": " + ( f.get() == 0 ? args[1] : "Unknown file type" ));
                }

            }


        } catch (Exception e){
            e.printStackTrace();
        }

        executor.shutdown();
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
