package encryptdecrypt;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String str = "";
        int key = 0;
        String mode = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-mode":
                    if (i + 1 < args.length && args[i+1].equals("dec")){
                        mode = "dec";
                    } else {
                        mode = "enc";
                    }
                    break;
                case "-key":

                    if (args[i+1].isBlank() || args[i+1].matches("-\\w+")){
                        key = sc.nextInt();
                    }
                    else {
                        key = Integer.parseInt(args[i + 1]);
                    }
                    break;
                case "-data":
                    if (args[i+1].isBlank() || args[i+1].matches("-\\w+")){
                        str = sc.next();
                    } else {
                        str = args[i + 1];
                    }
                    break;
            }
        }

        switch (mode) {
            case "enc":
                encrypt(str, key);
                break;
            case "dec":
                decrypt(str, key);
                break;
        }

    }


    private static void encrypt(String str, int key){

        char[] strArr = str.toCharArray();

        for(int i = 0; i < strArr.length; ++i){
            strArr[i] = (char)(strArr[i] + key);
        }

        System.out.println(new String(strArr));
    }

    private static void decrypt(String str, int key){

        key *= -1;

        StringBuilder result = new StringBuilder();

        for (char c : str.toCharArray()){
            result.append((char)(c+key));
        }

        System.out.println(result.toString());
    }
}
