package contacts;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static int currentRecord = -1;

    @SuppressWarnings("unchecked")
    public static void main(String[] args){
            String[] isInput = sc.nextLine().split(" ");
            if (isInput[0].equals("open")) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(isInput[1]))) {
                    Contact.contactsList = (LinkedList) in.readObject();
                    menu();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                menu();
            }
    }

    private static void menu(){
        char quit = 'x';
        String command;
        String name;
        String phone;

            while (quit != 'q') {
                currentRecord = -1;
                System.out.print("[menu] Enter action (add, remove, edit, search, count, list, exit): ");
                command = sc.nextLine().toLowerCase();
                switch (command) {
                    case "add":
                        System.out.print("[menu] Enter the type (person, organization): ");
                        String type = sc.nextLine();
                        if (type.equals("person")) {
                            System.out.print("Enter the name: ");
                            name = sc.nextLine();
                            System.out.print("Enter the surname: ");
                            String surname = sc.nextLine();
                            System.out.print("Enter the birth date: ");
                            String birthDate = sc.nextLine();
                            System.out.print("Enter the gender(M, F): ");
                            String gender = sc.nextLine().charAt(0) + "";
                            if (!gender.equals("M") && !gender.equals("F") || gender.isBlank()) {
                                gender = "[no data]";
                                System.out.println("Bad birth date!");
                            }
                            System.out.print("Enter the number: ");
                            phone = sc.nextLine();
                            phone = isNumberValid(phone);
                            Contact.contactsList.add(new Person(name, surname, birthDate, gender, phone));
                            System.out.println("Record added.");
                        } else if (type.equals("organization")) {
                            System.out.print("Enter the organization name: ");
                            name = sc.nextLine();
                            System.out.print("Enter the address: ");
                            String address = sc.nextLine();
                            System.out.print("Enter the number: ");
                            phone = sc.nextLine();
                            phone = isNumberValid(phone);
                            Contact.contactsList.add(new Organization(name, address, phone));
                            System.out.println("Record added.");
                        }
                        break;
                    case "remove":
                        remove();
                        break;
                    case "edit":
                        if (currentRecord != -1) {
                            Contact.contactsList.get(currentRecord).edit();
                        } else {
                            showAll();
                            System.out.print("[edit] Select a record: ");
                            int record = sc.nextInt() - 1;
                            Contact.contactsList.get(record).edit();
                            sc.nextLine();
                        }
                        break;
                    case "count":
                        System.out.println("The Phone Book has " + Contact.contactsList.size() + " records.");
                        break;
                    case "list":
                        showAll();
                        showDetails();
                        break;
                    case "search":
                        search();
                        break;
                    case "exit":
                        quit = 'q';
                        break;
                    default:
                        System.out.println("Wrong command.");
                        break;
                }
                updateFile();
            }
    }

    static void search(){

        System.out.print("Enter search query: ");
        Pattern p = Pattern.compile(".*" + sc.nextLine() + ".*", Pattern.CASE_INSENSITIVE);

        System.out.println("Results: ");
        for (int i = 0; i < Contact.contactsList.size(); i++) {
            Contact c = Contact.contactsList.get(i);
            Matcher m = p.matcher(c.showName());
            if (m.find()) {
                System.out.println((i + 1) + ". " + c.showName());
            }
        }

        System.out.print("[search] Enter action ([number], back, again)");
        String command = sc.nextLine().toLowerCase();

        if (command.equals("again")) {
            search();
        } else if (command.matches("//d+")){
            currentRecord = Integer.parseInt(command);
            System.out.println(Contact.contactsList.get(currentRecord - 1).toString());
        } else if (command.equals("back")){
            menu();
        }
    }

    static void remove(){
        if (Contact.contactsList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            showAll();
            System.out.print("Select a record: ");
            Contact.contactsList.remove(sc.nextInt() - 1);
            sc.nextLine();
            System.out.println("The record removed!");
        }
    }

    private static void showAll(){
        for (int i = 0; i < Contact.contactsList.size() ; i++) {
            System.out.println((i + 1) + ". " + Contact.contactsList.get(i).showName());
        }
    }

    private static void showDetails(){
        System.out.print("[list] Enter action ([number], back): ");
        String command = sc.nextLine().toLowerCase();
        if (command.matches("\\d+")){
            currentRecord = Integer.parseInt(command) - 1;
            System.out.println(Contact.contactsList.get(currentRecord).toString());
        } else if (command.equals("back")){
            menu();
        }

    }

    static String isNumberValid(String phoneNumber){
        Pattern p = Pattern.compile("^\\+?(?:\\+?(\\d{2,}))?([- (]*(\\d{2,})[- )]*)?((\\d{2,})[- ]*(\\d{2,}|" +
                "\\w{2,})(?:[- ]*(\\d{2,}|" +
                "\\w{2,}))?)*$|\\+?(\\w{2,}[ -])|" +
                "\\+?([(]?\\w{2,}[)])([ -]\\w{2,})|\\+?(\\w{2,})([ -][(]\\w{2,}[)])|\\+?([(]\\w{2,}[)])|");

        Matcher m = p.matcher(phoneNumber);

        if (!m.matches()) {
            System.out.println("Wrong number format!");
            return "[no number]";
        } else {
            return phoneNumber;
        }
    }

    private static void updateFile(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("phonebook.db"))){
            out.writeObject(Contact.contactsList);
        } catch (Exception e){
            e.getMessage();
        }
    }

}

