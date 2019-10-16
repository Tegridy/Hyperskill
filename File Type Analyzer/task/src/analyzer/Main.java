package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
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

            executor.submit( ()-> {

            for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isFile()) {

                        int currentSignaturePriority = -1;
                        int foundedSignatureIndex = -1;

                         byte[] allBytes = new byte[0];
                         try {
                        allBytes = Files.readAllBytes(fileEntry.toPath());
                       } catch (IOException e) {
                        System.out.println("Can't open the file.");
                        e.printStackTrace();
                       }

                        for (int i = 0; i < signatures.size(); i++) {

                            String[] element = signatures.get(i).split(";");

                            // 0 - Priority, 1 - Signature, 2 - Output text
                            byte[] signature = element[1].getBytes();

                            // Changing priority if there is need (files can have multiple signatures)
                            if (RabinKarp(allBytes, signature) && Integer.parseInt(element[0]) > currentSignaturePriority) {
                                    currentSignaturePriority = Integer.parseInt(element[0]);
                                    foundedSignatureIndex = i;
                            }

                           // KMP version below

                           // if (indexOf(allBytes, signature) == 0) {
                           //      if (Integer.parseInt(element[0]) > currentSignaturePriority)
                           //          currentSignaturePriority = Integer.parseInt(element[0]);
                           //       foundedSignatureIndex = i;
                           //    }
                        }

                        // Printing result
                        System.out.println(fileEntry.getName() + ": " + (foundedSignatureIndex != -1 ? signatures.get(foundedSignatureIndex).split(";")[2] : "Unknown file type"));

                }
            }
        });

        } catch (Exception e){
            System.out.println("There is problem with files. Check if path is correct.\n");
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


    public static byte byteToHash(byte b){
        // Hashing byte
        return (byte)(b - 'A' + 1);
    }

    public static boolean RabinKarp(byte[] AllText, byte[] RawPattern) {

        int a = 53;
        long m = 1_000_000_000 + 9;

        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        // Hashing pattern
        for (int i = 0; i < RawPattern.length; i++) {
            patternHash += byteToHash(RawPattern[i]) * pow;
            patternHash %= m;

            currSubstrHash += byteToHash(AllText[AllText.length - RawPattern.length + i]) * pow;
            currSubstrHash %= m;

            if (i != RawPattern.length - 1) {
                pow = pow * a % m;
            }
        }

        // Retuning true if pattern is found otherwise false
        for (int i = AllText.length; i >= RawPattern.length; i--) {
            if (patternHash == currSubstrHash) {

                for (int j = 0; j < RawPattern.length; j++) {
                    if (AllText[i - RawPattern.length + j] != RawPattern[j]) {
                       break;
                    }
                }
                return true;
            }

            // Using rolling hash
            if (i > RawPattern.length) {
                currSubstrHash = (currSubstrHash - byteToHash(AllText[i-1]) * pow % m + m) * a % m;
                currSubstrHash = (currSubstrHash + byteToHash(AllText[i - RawPattern.length - 1])) % m;
            }
        }

        return false;
    }

    private static void processSignatures(String filePath){

        // Opening file and creating list with patterns

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
