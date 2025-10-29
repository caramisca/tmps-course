package md.utm.tmps.lab1.domain.models;

/**
 * Abstract Beverage class - base for all beverages
 */
public abstract class Beverage {
    protected String name;
    protected String size;
    protected double price;
    
    public Beverage(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }
    
    public abstract void prepare();
    public abstract String getDescription();
    
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", name, size, price);
    }
}
