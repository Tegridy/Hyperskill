package contacts;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactBook {

    private static List<ContactBook> listOfContacts = new LinkedList<>();
    private static Scanner sc = new Scanner(System.in);

    public String phoneNumber;

     static void  addPerson(Person person){
         if (isNumberValid(person.getPhoneNumber()))
        listOfContacts.add(person);
         else {
             person.setPhoneNumber("[no number]");
             listOfContacts.add(person);
         }

         System.out.println("The record added.");
    }

    static void addOrganization(Organization org){
        if (isNumberValid(org.getPhoneNumber())) {
            listOfContacts.add(org);
        }
        else {
            org.setPhoneNumber("[no number]");
            listOfContacts.add(org);
        }

        System.out.println("The record added.");
    }

    private static void showContacts(){
         if (listOfContacts.isEmpty()){
             System.out.println("List is empty.");
         } else {
             for (int i = 0; i < listOfContacts.size(); i++) {
                 System.out.println(i + 1 + ". " + listOfContacts.get(i).getName());
             }
         }
    }

    static void showInfo(){
        showContacts();
        System.out.print("Select a record: ");
        int choice = sc.nextInt() - 1;
        System.out.println(listOfContacts.get(choice).toString());
    }

     private static boolean isNumberValid(String phoneNumber){
        Pattern p = Pattern.compile("^\\+?(?:\\+?(\\d{2,}))?([- (]*(\\d{2,})[- )]*)?((\\d{2,})[- ]*(\\d{2,}|" +
                "\\w{2,})(?:[- ]*(\\d{2,}|" +
                "\\w{2,}))?)*$|\\+?(\\w{2,}[ -])|" +
                "\\+?([(]?\\w{2,}[)])([ -]\\w{2,})|\\+?(\\w{2,})([ -][(]\\w{2,}[)])|\\+?([(]\\w{2,}[)])|");

        Matcher m = p.matcher(phoneNumber);

        if (m.matches()){
            return true;
        } else {
            System.out.println("Wrong number format!");
            return false;
        }
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
            if (listOfContacts.get(index).getClass() == Person.class) {
                System.out.print("Select a field (name, surname, birth date, gender, number): ");
                String command = sc.nextLine();
                Person p = (Person) listOfContacts.get(index);
                switch (command) {
                    case "name":
                        System.out.print("Enter name: ");
                        p.setFirstName(sc.nextLine());
                        break;
                    case "surname":
                        System.out.print("Enter surname: ");
                        p.setLastName(sc.nextLine());
                        break;
                    case "birth date":
                        System.out.print("Enter birth date: ");
                        p.setBirthDate(sc.nextLine());
                        break;
                    case "gender":
                        System.out.print("Enter gender: ");
                        p.setGender(sc.nextLine().charAt(0)+ "");
                        break;
                    case "number":
                        System.out.print("Enter number: ");
                        String phoneNumber = sc.nextLine();

                        if (isNumberValid(phoneNumber)) {
                            p.setPhoneNumber(phoneNumber);
                        }
                        else {
                            phoneNumber = "[no number]";
                            p.setPhoneNumber(phoneNumber);
                        }

                        break;
                }
                p.timeEdited = LocalDateTime.now();
            } else if (listOfContacts.get(index).getClass() == Organization.class) {
                System.out.print("Select a field (name, address, number): ");
                String command = sc.nextLine();
                Organization org = (Organization) listOfContacts.get(index);
                switch (command) {
                    case "name":
                        System.out.print("Enter name: ");
                        org.setName(sc.nextLine());
                        break;
                    case "surname":
                        System.out.print("Enter address: ");
                        org.setAddress(sc.nextLine());
                        break;
                    case "number":
                        System.out.print("Enter number: ");
                        String phoneNumber = sc.nextLine();

                        if (isNumberValid(phoneNumber)) {
                            org.setPhoneNumber(phoneNumber);
                        }
                        else {
                            phoneNumber = "[no number]";
                            org.setPhoneNumber(phoneNumber);
                        }

                        break;
                }
                org.timeEdited = LocalDateTime.now();
            }
            System.out.println("The record updated!");
        }
    }

    public String getName(){return "Default info.";}

    public static int getSize(){return listOfContacts.size();}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
