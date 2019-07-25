package contacts;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char quit = 'x';

        while (quit != 'q') {
            System.out.print("Enter action (add, remove, edit, count, list, exit): ");
            String command = sc.nextLine();
            switch (command) {
                case "add":
                    System.out.print("Enter the name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter the surname: ");
                    String surname = sc.nextLine();
                    System.out.print("Enter the number: ");
                    String phone = sc.nextLine();
                    ContactBook.addContact(name, surname, phone);
                    break;
                case "remove":
                    ContactBook.removeContact();
                    break;
                case "edit":
                    ContactBook.editContact();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + ContactBook.getSize() + " records.");
                    break;
                case "list":
                    ContactBook.showContacts();
                    break;
                case "exit":
                    quit = 'q';
                    //System.exit(0);
                    break;
                default:
                    System.out.println("Wrong command.");
                    break;
            }

        }
    }

}

