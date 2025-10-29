package md.utm.tmps.lab1.domain.models;

/**
 * Represents a complete meal with main dish and beverage
 */
public class Meal {
    private final Pizza pizza;
    private final Beverage beverage;
    private final String mealType;
    
    public Meal(Pizza pizza, Beverage beverage, String mealType) {
        this.pizza = pizza;
        this.beverage = beverage;
        this.mealType = mealType;
    }
    
    public Pizza getPizza() { return pizza; }
    public Beverage getBeverage() { return beverage; }
    public String getMealType() { return mealType; }
    
    public double getTotalPrice() {
        return beverage.getPrice() + 12.99; // Pizza base price + beverage
    }
    
    @Override
    public String toString() {
        return String.format("%s Meal:\n  - %s\n  - %s\n  Total: $%.2f",
                mealType, pizza, beverage, getTotalPrice());
    }
}
