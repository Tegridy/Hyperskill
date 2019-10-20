package search;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of people: ");
        int numOfData = sc.nextInt();
        sc.nextLine();

        String[] data = new String[numOfData];

        System.out.println("Enter all people: ");
        for (int i = 0; i < data.length; i++) {
            data[i] = sc.nextLine();
        }
        System.out.println();

        System.out.println("Enter the number of search queries: ");
        int numOfQueries = sc.nextInt();
        sc.nextLine();
        System.out.println();

        for (int i = 0; i < numOfQueries; i++) {
            System.out.println("Enter data to search people: ");
            String name = sc.nextLine().trim();

            System.out.println("Found people: ");
            for (String info : data) {
                if (info.contains(name)) {
                    System.out.println(info);
                }
            }
        }

    }

}
