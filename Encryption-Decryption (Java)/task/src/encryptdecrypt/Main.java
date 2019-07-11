package encryptdecrypt;


import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    //private static String alphabet = "abcdefghijklmnopqrstuvwxyz";


    public static void main(String[] args) {
        String command = sc.nextLine();

        switch (command){
            case "enc":
                encrypt();
                break;
            case "dec":
                decrypt();
                break;
        }
    }


    private static void encrypt(){
        String str = sc.nextLine();
        int key = Integer.parseInt(sc.nextLine());

        char[] strArr = str.toCharArray();

        for(int i = 0; i < strArr.length; ++i){
            strArr[i] = (char)(strArr[i] + key);
        }

        System.out.println(new String(strArr));
    }

    private static void decrypt(){
        String str = sc.nextLine();
        int key = Integer.parseInt(sc.nextLine());

        key *= -1;

        StringBuilder result = new StringBuilder();

        for (char c : str.toCharArray()){
            result.append((char)(c+key));
        }

        System.out.println(result.toString());

    }
}
