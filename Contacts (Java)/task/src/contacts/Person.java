package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Person extends Contact {

    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private LocalDateTime timeCreated;
    LocalDateTime timeEdited = LocalDateTime.of(2019, 1, 1, 0,0);

    public Person(String firstName, String lastName, String birthDate, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.timeCreated = LocalDateTime.now();
    }

    @Override
    String showName() {
        return firstName + " " + lastName;
    }

    @Override
    void edit() {
        System.out.print("Select a field (name, surname, birth date, gender, number): ");
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        switch (command) {
            case "name":
                System.out.print("Enter name: ");
                this.firstName = sc.nextLine();
                break;
            case "surname":
                System.out.print("Enter surname: ");
                this.lastName = sc.nextLine();
                break;
            case "birth date":
                this.birthDate = sc.nextLine();
                break;
            case "gender":
                this.gender =sc.nextLine();
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
        return "Name: " + firstName + "\nSurname: " + lastName + "\nBirth date: "
                + birthDate + "\nGender: " + gender + "\nNumber: " + phoneNumber
                + "\nTime created: " + timeCreated + "\nTime last edit: " + timeEdited;
    }
}
