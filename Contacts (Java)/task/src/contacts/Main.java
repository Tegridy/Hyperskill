package contacts;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char quit = 'x';

        while (quit != 'q'){
        System.out.print("Enter action (add, remove, edit, count, info, exit): ");
            String command = sc.nextLine();
            switch (command) {
                case "add":
                    System.out.print("Enter the type (person, organization): ");
                    String type = sc.nextLine();
                    if (type.equals("person")) {
                        System.out.print("Enter the name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter the surname: ");
                        String surname = sc.nextLine();
                        System.out.print("Enter the birth date: ");
                        String birthDate = sc.nextLine();
                        System.out.print("Enter the gender(M, F): ");
                        String gender = sc.nextLine().charAt(0) + "";
                        System.out.print("Enter the number: ");
                        String phone = sc.nextLine();
                        Person person = new Person(name, surname, birthDate, gender, phone);
                        ContactBook.addPerson(person);
                    } else if (type.equals("organization")) {
                        System.out.print("Enter the organization name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter the address: ");
                        String address = sc.nextLine();
                        System.out.print("Enter the number: ");
                        String phone = sc.nextLine();
                        Organization org = new Organization(name, address, phone);
                        ContactBook.addOrganization(org);
                    }
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
                case "info":
                    ContactBook.showInfo();
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

