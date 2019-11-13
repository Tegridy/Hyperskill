package budget;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Category implements Serializable {

    String name;
    double price;

    static ArrayList<Category> completeList = new ArrayList<>();

     Category(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " $" + price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
