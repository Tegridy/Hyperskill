package contacts;

import java.util.LinkedList;
import java.util.List;

public class ContactBook {

    private List<Contact> listOfContacts = new LinkedList<>();

    private Contact person;

    ContactBook(Contact person) {
        this.person = person;
    }

    public void addContact(Contact person){
        listOfContacts.add(person);
        System.out.println("A Phone Book with a single record created!");
    }

    private String removeContact(Contact person){
        return "";
    }
}
