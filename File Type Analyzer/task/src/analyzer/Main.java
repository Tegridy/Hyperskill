package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static List<String> signatures = new ArrayList<>();

    public static void main(String[] args) {
        //int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        processSignatures(args[0]);

        try {

            String folderPath = args[1];

            final File folder = new File(folderPath);

            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile()) {

                    executor.submit( ()-> {

                        int currentSignaturePriority = -1;
                        int foundedSignatureIndex = -1;

                        for (int i = 0; i < signatures.size(); i++) {
                            byte[] allBytes = new byte[0];
                            try {
                                allBytes = Files.readAllBytes(fileEntry.toPath());
                            } catch (IOException e) {
                                System.out.println("Can't open the file.");
                                e.printStackTrace();
                            }

                            // 0 - Priority, 1 - Signature, 2 - Output text
                            String[] element = signatures.get(i).split(";");

                            byte[] signature = element[1].getBytes();

                            if (indexOf(allBytes, signature) == 0) {
                                if (Integer.parseInt(element[0]) > currentSignaturePriority)
                                    currentSignaturePriority = Integer.parseInt(element[0]);
                                foundedSignatureIndex = i;
                            }
                        }

                        System.out.println(fileEntry.getName() + ": " + (foundedSignatureIndex != -1 ? signatures.get(foundedSignatureIndex).split(";")[2] : "Unknown file type"));

                    });
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

    private static void processSignatures(String filePath){

        try (Scanner sc = new Scanner(new File(filePath))){

            while (sc.hasNextLine()){
                signatures.add(sc.nextLine().replaceAll("\"", ""));
            }

        } catch (Exception e){
            System.out.println("Can't process the file.");
            e.printStackTrace();
        }

    }
}
