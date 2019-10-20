package search;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    private static String[] data;

    public static void main(String[] args) {
        readData();
        printMenu();
    }

    private static void searchData() {
            System.out.println("Enter a name or email to search all suitable people: ");
            String name = sc.nextLine().toLowerCase();

            System.out.println("Found people: ");
            for (String info : data) {
                if (info.toLowerCase().contains(name)) {
                    System.out.println(info);
                }
            }
    }

    private static void readData(){
        System.out.println("Enter the number of people: ");
        int numOfData = sc.nextInt();
        sc.nextLine();

        data = new String[numOfData];

        System.out.println("Enter all people: ");
        for (int i = 0; i < data.length; i++) {
            data[i] = sc.nextLine();
        }
        System.out.println();
    }

    private static void printMenu(){
        boolean quit = false;
        while (!quit) {

            System.out.println(" === Menu === ");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    searchData();
                    break;
                case 2:
                    printAll();
                    break;
                case 0:
                    System.out.println("Bye!");
                    quit = true;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
            System.out.println();
        }
    }

    private static void printAll(){
        System.out.println("=== List of people ===");
        for(String e : data){
            System.out.println(e);
        }

        System.out.println();
    }
}
