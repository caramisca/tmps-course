package md.utm.tmps.lab2.domain.models;

/**
 * Concrete Component - Base Pizza implementation
 * This is the core object that will be decorated
 */
public class BasePizza implements PizzaComponent {
    private final String size;
    private final String crustType;
    private final double basePrice;
    
    public BasePizza(String size, String crustType) {
        this.size = size;
        this.crustType = crustType;
        this.basePrice = calculateBasePrice(size);
    }
    
    private double calculateBasePrice(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> 8.99;
            case "medium" -> 10.99;
            case "large" -> 12.99;
            case "extra large" -> 14.99;
            default -> 10.99;
        };
    }
    
    @Override
    public String getDescription() {
        return size + " " + crustType + " crust pizza";
    }
    
    @Override
    public double getCost() {
        return basePrice;
    }
    
    @Override
    public void prepare() {
        System.out.println("  → Preparing " + crustType + " crust");
        System.out.println("  → Baking " + size + " size pizza");
    }
    
    public String getSize() {
        return size;
    }
    
    public String getCrustType() {
        return crustType;
    }
}
