package md.utm.tmps.lab1.domain.models;

/**
 * Coffee beverage implementation
 */
public class Coffee extends Beverage {
    private String coffeeType;
    
    public Coffee(String size, String coffeeType) {
        super("Coffee", size, calculatePrice(size));
        this.coffeeType = coffeeType;
    }
    
    private static double calculatePrice(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> 2.50;
            case "medium" -> 3.50;
            case "large" -> 4.50;
            default -> 3.50;
        };
    }
    
    @Override
    public void prepare() {
        System.out.println("Brewing " + coffeeType + " coffee...");
        System.out.println("Adding hot water...");
        System.out.println("Coffee is ready!");
    }
    
    @Override
    public String getDescription() {
        return coffeeType + " " + name + " - " + size;
    }
    
    public String getCoffeeType() { return coffeeType; }
}
