package analyzer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map<byte[], String> extensionsMap = new HashMap<>();

        byte[] pdfSignature = args[1].getBytes();

        extensionsMap.put(pdfSignature, args[2]);


        try {
            byte[] allBytes = Files.readAllBytes(Path.of(args[0]));

            System.out.println(find(allBytes, pdfSignature) == 1 ? args[2] : "Unknown file type");

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    static int find(byte[] allBytes, byte[] fileType){

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

//    private static int indexOf(byte[] data, byte[] pattern) {
//        if (data.length == 0) return -1;
//
//        int[] failure = computeFailure(pattern);
//        int j = 0;
//
//        for (int i = 0; i < data.length; i++) {
//            while (j > 0 && pattern[j] != data[i]) {
//                j = failure[j - 1];
//            }
//            if (pattern[j] == data[i]) { j++; }
//            if (j == pattern.length) {
//                return i - pattern.length + 1;
//            }
//        }
//        return -1;
//    }
//
//    private static int[] computeFailure(byte[] pattern) {
//        int[] failure = new int[pattern.length];
//
//        int j = 0;
//        for (int i = 1; i < pattern.length; i++) {
//            while (j > 0 && pattern[j] != pattern[i]) {
//                j = failure[j - 1];
//            }
//            if (pattern[j] == pattern[i]) {
//                j++;
//            }
//            failure[i] = j;
//        }
//
//        return failure;
//    }


}
