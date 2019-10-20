package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    private static ArrayList<String> data = new ArrayList<>();

    public static void main(String[] args) {
        if (args[0].equals("--data") || args[0].contains("--db")) {
            readData(args[1]);
        } else {
            System.out.println("Wrong path to file.");
        }
        printMenu();
    }

    private static void searchData() {
            System.out.println("Enter a name or email to search all suitable people: ");
            String name = sc.nextLine().toLowerCase();

            System.out.println("Found people: ");
            for (String e : data) {
                if (e.toLowerCase().contains(name)) {
                    System.out.println(e);
                }
            }
    }

    private static void readData(String path){
        File f = new File(path);
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
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
            System.out.println();
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
