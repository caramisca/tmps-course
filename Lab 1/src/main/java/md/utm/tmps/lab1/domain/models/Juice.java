package md.utm.tmps.lab1.domain.models;

/**
 * Juice beverage implementation
 */
public class Juice extends Beverage {
    private String fruit;
    
    public Juice(String size, String fruit) {
        super("Juice", size, calculatePrice(size));
        this.fruit = fruit;
    }
    
    private static double calculatePrice(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> 3.00;
            case "medium" -> 4.00;
            case "large" -> 5.00;
            default -> 4.00;
        };
    }
    
    @Override
    public void prepare() {
        System.out.println("Squeezing fresh " + fruit + "...");
        System.out.println("Filtering juice...");
        System.out.println("Adding ice...");
        System.out.println("Juice is ready!");
    }
    
    @Override
    public String getDescription() {
        return fruit + " " + name + " - " + size;
    }
    
    public String getFruit() { return fruit; }
}
