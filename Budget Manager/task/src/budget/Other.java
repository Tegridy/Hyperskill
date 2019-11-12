package budget;

public class Other extends Category {
    public Other(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return name + " $" + price;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }
}
