package budget;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

    private static double accountBalance = 0.0;
    //private static double totalSum = 0;

    public static void main(String[] args) {
        // write your code here
        printMenu();
    }

    private static void printMenu() {
        boolean quit = false;
        while (!quit) {

            System.out.println();
            System.out.println(
                    "Choose your action:\n" +
                            "1) Add income\n" +
                            "2) Add purchase\n" +
                            "3) Show list of purchases\n" +
                            "4) Balance\n" +
                            "0) Exit");

            int choice = sc.nextInt();
            //sc.nextLine();

            System.out.println();

            switch (choice) {
                case 1:
                    System.out.println("Enter income: ");
                    addIncome(sc.nextDouble());
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    chooseCategoryToPrint();
                    break;
                case 4:
                    System.out.println("Balance: $" + accountBalance);
                    break;
                case 0:
                    System.out.println("Bye!");
                    quit = true;
                    System.exit(1);
                    break;
                default:
                    System.out.println("Wrong choice. Try again!");
                    printMenu();
                    break;
            }
        }
    }

    private static void addIncome(double amount){
        accountBalance += amount;
        System.out.println("Income was added!");
    }

    private static void addPurchase(){

        System.out.println(
                "Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 5) printMenu();

        System.out.println("Enter purchase name: ");
        String purchaseName = sc.nextLine();
        System.out.println("Enter its price: ");
        double price = sc.nextDouble();


        if (accountBalance >= price) {
            accountBalance -= price;
        } else {
            System.out.println("Not enough money!");
            printMenu();
        }

        switch (choice){
            case 1:
                Food f = new Food(purchaseName, price);
                Category.all.add(f);
                break;
            case 2:
                Clothes c = new Clothes(purchaseName, price);
                Category.all.add(c);
                break;
            case 3:
                Entertainment e = new Entertainment(purchaseName, price);
                Category.all.add(e);
                break;
            case 4:
                Other o = new Other(purchaseName, price);
                Category.all.add(o);
                break;
        }
        System.out.println("\nPurchase added.");
    }

    private static void chooseCategoryToPrint(){
        System.out.println(
                "Choose the type of purchase\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other\n" +
                        "5) All\n" +
                        "6) Back");

        int choice = sc.nextInt();

        switch (choice){
            case 1:
                System.out.println("Food: ");
                printByCategory(Food.class);
                break;
            case 2:
                System.out.println("Clothes: ");
                printByCategory(Clothes.class);
                break;
            case 3:
                System.out.println("Entertainment: ");
                printByCategory(Entertainment.class);
                break;
            case 4:
                System.out.println("Other: ");
                printByCategory(Other.class);
                break;
            case 5:
                System.out.println("All: ");
                printAll();
                break;
            case 6:
                printMenu();
                break;
        }
    }

    private static void printByCategory(Class c){

        double totalSum = 0;

        if (Category.all.size() > 0) {

            for (int i = 0; i < Category.all.size(); i++) {

                Category cat = Category.all.get(i);

                if (cat.getClass() == c) {
                    System.out.println(cat.toString());
                    totalSum += cat.getPrice();
                }
            }

            System.out.println("Total: $" + totalSum);
        } else {
            System.out.println("Purchase list is empty!");
        }

        }

    private static void printAll(){
        double totalSum = 0;

        if (Category.all.size() > 0) {
            for (Category c : Category.all) {
                System.out.println(c.toString());
                totalSum += c.getPrice();
            }

            System.out.println("\nTotal: $" + totalSum);
        } else {
            System.out.println("Purchase list is empty!");
        }
    }
}
