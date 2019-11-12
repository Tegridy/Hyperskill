package budget;

import java.util.ArrayList;

public abstract class Category {

    String name;
    double price;

    static ArrayList<Category> all = new ArrayList<>();

     Category(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
