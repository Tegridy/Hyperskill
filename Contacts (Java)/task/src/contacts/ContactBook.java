package contacts;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class ContactBook {

    private static final Scanner sc = new Scanner(System.in);
    private static final List<Contact> listOfContacts = new LinkedList<>();

    static void addContact(String name, String surname, String phoneNumber){
        Contact contact = new Contact.ContactBuilder().setFirstName(name)
                .setLastName(surname).setPhoneNumber(phoneNumber).build();
        listOfContacts.add(contact);
        System.out.println("The record added.");
    }

    static void removeContact(){
        if (listOfContacts.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            showContacts();
            System.out.print("Select a record: ");
            listOfContacts.remove(sc.nextInt() - 1);
            sc.nextLine();
            System.out.println("The record removed!");
        }
    }

    static void editContact(){
        if (listOfContacts.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            showContacts();
            System.out.print("Select a record: ");
            int index = sc.nextInt() - 1;
            sc.nextLine();

            System.out.print("Select a field (name, surname, number): ");
            String command = sc.nextLine();
            Contact oldCont = listOfContacts.get(index);

            switch (command) {
                case "name":
                    System.out.print("Enter name: ");
                    listOfContacts.set(index, new Contact.ContactBuilder().setFirstName(sc.next()).setLastName(oldCont.getLastName())
                            .setPhoneNumber(oldCont.getPhoneNumber()).build());
                    break;
                case "surname":
                    System.out.print("Enter surname: ");
                    listOfContacts.set(index, new Contact.ContactBuilder().setFirstName(oldCont.getFirstName()).setLastName(sc.next())
                            .setPhoneNumber(oldCont.getPhoneNumber()).build());
                    break;
                case "number":
                    System.out.print("Enter number: ");
                    String number = sc.nextLine();
                    listOfContacts.set(index, new Contact.ContactBuilder().setFirstName(oldCont.getFirstName()).setLastName(oldCont.getLastName())
                            .setPhoneNumber(number).build());
                    break;
            }
            System.out.println("The record updated!");
        }
    }

    static void showContacts(){
        if (listOfContacts.isEmpty()){
            System.out.println("List is empty.");
        } else {
            for (int i = 0; i < listOfContacts.size(); i++) {
                System.out.println((i + 1) + ". " + listOfContacts.get(i).toString());
            }
        }
    }

    static int getSize(){
        return listOfContacts.size();
    }


}
