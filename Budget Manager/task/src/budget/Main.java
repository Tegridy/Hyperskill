package budget;

import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

    static double accountBalance = 0.0;
    //private static double totalSum = 0;

    public static void main(String[] args) {
        // write your code here
        printMainMenu();
    }

    private static void printMainMenu() {
        boolean quit = false;
        while (!quit) {

            System.out.println();
            System.out.println(
                    "Choose your action:\n" +
                            "1) Add income\n" +
                            "2) Add purchase\n" +
                            "3) Show list of purchases\n" +
                            "4) Balance\n" +
                            "5) Save\n" +
                            "6) Load\n" +
                            "7) Analyze (Sort)\n" +
                            "0) Exit");

            int choice = sc.nextInt();
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
                case 5:
                    FileOperations.saveToFile();
                    break;
                case 6:
                    FileOperations.readFromFile();
                    break;
                case 7:
                    analyze();
                    break;
                case 0:
                    System.out.println("Bye!");
                    quit = true;
                    System.exit(1);
                    break;
                default:
                    System.out.println("Wrong choice. Try again!");
                    printMainMenu();
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

        if (choice == 5) printMainMenu();

        System.out.println("Enter purchase name: ");
        String purchaseName = sc.nextLine();
        System.out.println("Enter its price: ");
        double price = sc.nextDouble();


        if (accountBalance >= price) {
            accountBalance -= price;
        } else {
            System.out.println("Not enough money!");
            printMainMenu();
        }

        switch (choice){
            case 1:
                Food f = new Food(purchaseName, price);
                Category.completeList.add(f);
                break;
            case 2:
                Clothes c = new Clothes(purchaseName, price);
                Category.completeList.add(c);
                break;
            case 3:
                Entertainment e = new Entertainment(purchaseName, price);
                Category.completeList.add(e);
                break;
            case 4:
                Other o = new Other(purchaseName, price);
                Category.completeList.add(o);
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
                printMainMenu();
                break;
        }
    }

    private static void printByCategory(Class receivedClass){

        double totalSum = 0;

        if (!Category.completeList.isEmpty()) {

            for (int i = 0; i < Category.completeList.size(); i++) {

                Category cat = Category.completeList.get(i);

                if (cat.getClass() == receivedClass) {
                    System.out.println(cat.toString());
                    totalSum += cat.getPrice();
                }
            }

            System.out.println("\nTotal: $" + totalSum);
            System.out.println();
        } else {
            System.out.println("Purchase list is empty!");
        }

        }

    private static void printAll(){
        double totalSum = 0;

        if (!Category.completeList.isEmpty()) {
            for (Category c : Category.completeList) {
                System.out.println(c.toString());
                totalSum += c.getPrice();
            }

            System.out.println("\nTotal: $" + totalSum);
        } else {
            System.out.println("Purchase list is empty!");
        }
    }

    private static void analyze(){
        System.out.println(
                "How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");

        int choice = sc.nextInt();
        if (choice == 4) printMainMenu();

        Category.completeList.sort(Comparator.comparingDouble(Category::getPrice).reversed());

        if (!Category.completeList.isEmpty()) {

            switch (choice) {
                case 1:
                    System.out.println("All: ");
                    for (Category c : Category.completeList) {
                        System.out.println(c);
                    }
                    System.out.println("\nTotal: $" + Category.completeList.stream().mapToDouble(Category::getPrice).sum());
                    System.out.println();
                    break;
                case 2:
                    ArrayList<Category> cat = Category.completeList;

                    System.out.println("Types: ");
                    System.out.println("Food - $" + cat.stream().filter(o -> o.getClass() == Food.class).mapToDouble(Category::getPrice).sum());
                    System.out.println("Clothes - $" + cat.stream().filter(o -> o.getClass() == Clothes.class).mapToDouble(Category::getPrice).sum());
                    System.out.println("Entertainment  - $" + cat.stream().filter(o -> o.getClass() == Entertainment.class).mapToDouble(Category::getPrice).sum());
                    System.out.println("Other - $" + cat.stream().filter(o -> o.getClass() == Other.class).mapToDouble(Category::getPrice).sum());

                    System.out.println("\nTotal sum - $" + cat.stream().mapToDouble(Category::getPrice).sum());
                    System.out.println();
                    break;
                case 3:
                    System.out.println(
                            "Choose the type of purchase\n" +
                                    "1) Food\n" +
                                    "2) Clothes\n" +
                                    "3) Entertainment\n" +
                                    "4) Other\n"
                    );

                    int choice2 = sc.nextInt();
                    switch (choice2) {
                        case 1:
                            printByCategory(Food.class);
                            break;
                        case 2:
                            printByCategory(Clothes.class);
                            break;
                        case 3:
                            printByCategory(Entertainment.class);
                            break;
                        case 4:
                            printByCategory(Other.class);
                            break;
                    }
                    analyze();
                    break;
                default:
                    System.out.println("Wrong choice. Try again.");
                    analyze();
                    break;
            }
            analyze();
        } else {
            System.out.println("\nPurchase list is empty! \n");
            analyze();
        }
    }

}
