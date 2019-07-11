package encryptdecrypt;


import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int alphaLen = alphabet.length() - 1;

        String str = sc.nextLine();
        int key = Integer.parseInt(sc.nextLine());
        int shift;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length() ; i++) {

                int index = alphabet.indexOf(str.charAt(i));

                if (str.charAt(i) == ' ' || (str.charAt(i) + "").matches("[\\!\\?\\#\\@\\&\\*\\$]")){
                    result.append(str.charAt(i));
                    continue;
                }

                shift = index + key;

                while (shift > alphaLen){
                    shift = shift - alphaLen - 1;
                }

                result.append(alphabet.charAt(shift));
        }

        System.out.println(result.toString());

    }
}
