package md.utm.tmps.lab3.domain.models;

/**
 * Base class representing a computer component
 */
public abstract class Component {
    private final String name;
    private final double price;
    private final String category;

    public Component(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", name, category, price);
    }
}
