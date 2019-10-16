package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        String toFind = sc.nextLine();

        String[] textArray = text.split(" ");

        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i].equals(toFind)){
                System.out.println(i+1);
                break;
            } else if (i == textArray.length-1){
                System.out.println("Not found");
                break;
            }
        }

    }
}
