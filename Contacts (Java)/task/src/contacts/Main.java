package contacts;


import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the name of the person:");
        String name = sc.nextLine();
        System.out.println("Enter the surname of the person:");
        String surname = sc.nextLine();
        System.out.println("Enter the number:");
        String phoneNumber = sc.nextLine();

        Contact p1 = new Contact(name, surname, phoneNumber);
        ContactBook book = new ContactBook(p1);
        book.addContact(p1);
    }
}
