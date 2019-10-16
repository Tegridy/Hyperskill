package encryptdecrypt;


import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static String filePath;

    public static void main(String[] args) {

        String str = "";
        int key = 0;
        String mode = "";


        boolean arg = false; // checks if is need to file write
        boolean alg = false; // true = shift algorithm, false = unicode algorithm

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    if (i + 1 < args.length && args[i + 1].equals("dec")) {
                        mode = "dec";
                    } else {
                        mode = "enc";
                    }
                    break;
                case "-key":
                    if (args[i + 1].isBlank() || args[i + 1].matches("-\\w+")) {
                        key = sc.nextInt();
                    } else {
                        key = Integer.parseInt(args[i + 1]);
                    }
                    break;
                case "-data":
                    if (args[i + 1].isBlank() || args[i + 1].matches("-\\w+")) {
                        str = sc.next();
                    } else {
                        str = args[i + 1];
                    }
                    break;
                case "-in":
                    if (args[i + 1].isBlank() || args[i + 1].matches("-\\w+")) {
                        str = sc.nextLine();
                    } else {
                        filePath = args[i + 1];

                        File file = new File(filePath);

                        try (Scanner scan = new Scanner(file)) {
                            str = scan.nextLine();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "-out":
                    if (args[i + 1].isBlank() || args[i + 1].matches("-\\w+")) {
                        arg = false;
                    } else {
                        arg = true;
                        filePath = args[i + 1];
                    }
                    break;
                case "-alg":
                    if (args[i + 1].equals("unicode")) {
                        alg = true;
                    }
                    break;
            }
        }

        if (alg) {
            if (mode.equals("dec")) {
                key *= -1;
                unicode(str, key, arg);
            } else {
                unicode(str, key, arg);
            }

        } else {
            if (mode.equals("dec")) {
                key = 26 - key;
                shift(str, key, arg);
            } else {
                shift(str, key, arg);
            }
        }

    }

    private static void unicode(String str, int key, boolean arg) {

        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            result.append((char) (c + key));
        }

        toFile(arg, result);
    }


    private static void shift(String str, int key, boolean arg) {

        String alpha = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (alpha.indexOf(c) == -1) {
                result.append(c);
                continue;
            }
            char cTransformed = alpha.charAt((Character.toLowerCase(c) - 'a' + key) % 26);
            result.append(cTransformed);
        }

        toFile(arg, result);
    }

    private static void toFile(boolean arg, StringBuilder result) {
        if (arg && !filePath.isBlank()) {
            File file = new File(filePath);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(result.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println(result.toString());
        }
    }


    /*
    private static void encrypt(String str, int key, boolean arg, boolean alg){

        char[] strArr = str.toCharArray();

        for(int i = 0; i < strArr.length; ++i){
            strArr[i] = (char)(strArr[i] + key);
        }
        if (arg && !filePath.isBlank()){
            File file = new File(filePath);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(new String(strArr));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println(new String(strArr));
            }
        }

    private static void decrypt(String str, int key, boolean arg){

        key *= -1;

        StringBuilder result = new StringBuilder();

        for (char c : str.toCharArray()){
            result.append((char)(c+key));
        }

        if (arg && !filePath.isBlank()){
            File file = new File(filePath);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(result.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println(result.toString());
        }
    }

     */
}
