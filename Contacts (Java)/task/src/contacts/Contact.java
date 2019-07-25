package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact{

    private String firstName;
    private String lastName;
    private String phoneNumber;



    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + phoneNumber;
    }

    public static final class ContactBuilder {
        private String firstName;
        private String lastName;
        private String phoneNumber = "";

        public ContactBuilder() {
        }

        public ContactBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        private boolean isNumberValid(String phoneNumber){
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
        public Contact build() {
            Contact c = new Contact();
            c.firstName = firstName;
            c.lastName = lastName;
            c.phoneNumber = phoneNumber;

            if (isNumberValid(phoneNumber)) {
                return c;
            } else {
                c.phoneNumber = "[no number]";
                return c;
            }
        }
    }
}


