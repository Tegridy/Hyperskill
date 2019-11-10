package budget;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static double accountBalance = 0.0;
    private static ArrayList<String> shopList = new ArrayList<>();
    private static double sum = 0;

    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

        boolean q = false;
        while (!q){

            System.out.println();
            printMenu();

            int choice = sc.nextInt();
            sc.nextLine();

            System.out.println();

            switch (choice){
                case 1:
                    System.out.println("Enter income: ");
                    addIncome(sc.nextDouble());
                    break;
                case 2:
                    System.out.println("Enter purchase name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter its price: ");
                    double price = sc.nextDouble();
                    addPurchase(name, price);
                    break;
                case 3:
                    showPurchasesList();
                    break;
                case 4:
                    System.out.println("Balance: $" + accountBalance);
                    break;
                case 0:
                    System.out.println("Bye!");
                    q = true;
                    break;
                default:
                    System.out.println("Wrong choice. Try again!");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println(
                "Choose your action:\n" +
                        "1) Add income\n" +
                        "2) Add purchase\n" +
                        "3) Show list of purchases\n" +
                        "4) Balance\n" +
                        "0) Exit");
    }

    private static void addIncome(double amount){
        accountBalance += amount;
        System.out.println("Income was added!");
    }

    private static void showPurchasesList(){
        if (shopList.isEmpty()){
            System.out.println("Purchase list is empty.");
        } else {
            for (String s : shopList) {
                System.out.println(s);
            }

            System.out.println("Total sum: $" + sum);
        }
    }

    private static void addPurchase(String name, double price){
        shopList.add(name + " " + price);
        sum += price;
        accountBalance -= price;
        System.out.println("Purchase was added!");
    }
}
