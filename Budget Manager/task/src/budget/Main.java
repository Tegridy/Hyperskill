package budget;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);

        //ArrayList<String> shopList = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();

        while (sc.hasNextLine()){
            String product = sc.nextLine();
            //shopList.add(product);
            System.out.println(product);
            prices.add(Double.parseDouble(product.split("\\$")[1]));
        }




        System.out.println("Total: $" + prices.stream().reduce(0.0, Double::sum));
    }
}
