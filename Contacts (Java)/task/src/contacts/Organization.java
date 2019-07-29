package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Organization extends ContactBook {
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDateTime timeCreated;
    LocalDateTime timeEdited = LocalDateTime.of(2019, 1, 1, 0, 0);

    public Organization(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        timeCreated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Organization name: " + name + "\nAddress: " + address + "\nNumber: " + phoneNumber
                + "\nTime created: " + timeCreated + "\nTime last edit: " + timeEdited;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
