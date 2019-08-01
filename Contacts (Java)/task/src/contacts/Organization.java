package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Organization extends Contact {
    private String name;
    private String address;
    private LocalDateTime timeCreated;
    LocalDateTime timeEdited = LocalDateTime.of(2019, 1, 1, 0,0);

    public Organization(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.timeCreated = LocalDateTime.now();
    }

    @Override
    String showName() {
        return name;
    }

    @Override
    void edit() {
        System.out.print("Select a field (name, address, number): ");
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        switch (command) {
            case "name":
                System.out.print("Enter name: ");
                this.name = sc.nextLine();
                break;
            case "address":
                System.out.print("Enter address: ");
                this.address = sc.nextLine();
                break;
            case "number":
                System.out.print("Enter number: ");
                this.phoneNumber = Main.isNumberValid(sc.nextLine());
                break;
        }
        timeEdited = LocalDateTime.now();
        System.out.println("Saved");
    }

    @Override
    public String toString() {
        return "Organization name: " + name + "\nAddress: " + address + "\nNumber: " + phoneNumber
                + "\nTime created: " + timeCreated + "\nTime last edit: " + timeEdited;
    }
}
