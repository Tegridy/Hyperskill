package contacts;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

abstract class Contact implements Serializable {

    String phoneNumber;

    static List<Contact> contactsList = new LinkedList<>();

    abstract String showName();
    abstract void edit();
}
