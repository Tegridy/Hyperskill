package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    private static HashMap<Integer, String> namesMap = new HashMap<>(); // using map as it says in task
    private static ArrayList<String> results = new ArrayList<>();

    public static void main(String[] args) {
        if (args[0].equals("--data") || args[0].equals("--db")) {
            readData(args[1]);
        } else {
            System.out.println("Wrong path to file.");
        }
        printMenu();
    }

    private static void searchData() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE ");
        String command = sc.nextLine().toUpperCase();

        System.out.println("Enter a name or email to search all suitable people: ");
        String[] data = sc.nextLine().toLowerCase().split(" ");

        switch (command){
            case "ANY":
                any(data);
                break;
            case "ALL":
                all(data);
                break;
            case "NONE":
                none(data);
                break;
            default:
                System.out.println("Wrong command.");
                searchData();
                break;
        }
    }

    private static void any(String[] data){
        for(String contact : namesMap.values()){
            for (String name : data) {
                if (contact.toLowerCase().contains(name)) {
                    results.add(contact);
                }
            }
        }
        printResults();
    }

    private static void all(String[] data) {
        for(String contact : namesMap.values()){
            int i = 0; // if number of found words equals length of query size add it to result list

            for (String name : data) {
                if (!contact.toLowerCase().contains(name)) {
                    break;
                } else {
                    i++;
                    if (i == data.length) {
                      results.add(contact);
                    }
                }
            }
        }
        printResults();
    }

    private static void none(String[] data){
        for(String contact : namesMap.values()){
            int i = 0;

            for (String name : data) {
                if (!contact.toLowerCase().contains(name)) {
                    i++;
                    if (i == data.length) {
                        results.add(contact);
                    }
                }
            }
        }
        printResults();
    }

    private static void printResults() {
        if (results.size() > 0) {
            System.out.println(results.size() + " persons found: ");

            for (String elem : results) {
                System.out.println(elem);
            }
        } else {
            System.out.println("No matching people found.");
        }

        results.clear();
    }

    private static void readData(String path){
        // path = "C:\\IdeaProjects\\Simple Search Engine\\Simple Search Engine\\task\\src\\search\\data.txt";
        File f = new File(path);

        try (Scanner scanner = new Scanner(f)) {
            int i = 0;
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                namesMap.put(i, s);
                i++;
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
        for(String elem : namesMap.values()){
            System.out.println(elem);
        }

        System.out.println();
    }
}
