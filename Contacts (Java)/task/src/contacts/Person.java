package contacts;

import java.time.LocalDateTime;

public class Person extends ContactBook {

    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private LocalDateTime timeCreated;
    LocalDateTime timeEdited = LocalDateTime.of(2019, 1, 1, 0, 0);

    public Person(String firstName, String lastName, String birthDate, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        if (birthDate.isBlank()) {
            System.out.println("Bad birth date!");
            this.birthDate = "[no data]";
        } else this.birthDate = birthDate;
        if (!gender.equals("M") && !gender.equals("M")) {
            System.out.println("Bad gender!");
            this.gender = "[no data]";
        } else this.gender = gender;
        this.phoneNumber = phoneNumber;
        timeCreated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Name: " + firstName + "\nSurname: " + lastName + "\nBirth date: "
                + birthDate + "\nGender: " + gender + "\nNumber: " + phoneNumber
                + "\nTime created: " + timeCreated + "\nTime last edit: " + timeEdited;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
