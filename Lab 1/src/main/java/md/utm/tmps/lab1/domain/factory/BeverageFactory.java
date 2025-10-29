package md.utm.tmps.lab1.domain.factory;

import md.utm.tmps.lab1.domain.models.Beverage;
import md.utm.tmps.lab1.domain.models.Coffee;
import md.utm.tmps.lab1.domain.models.Tea;
import md.utm.tmps.lab1.domain.models.Juice;

/**
 * Factory Method Pattern Demonstration
 * 
 * PATTERN: Defines an interface for creating an object, but lets subclasses decide
 * which class to instantiate. Factory Method lets a class defer instantiation to subclasses.
 * 
 * USE CASE: BeverageFactory creates different types of beverages (Coffee, Tea, Juice)
 * based on the beverage type, encapsulating the creation logic and making it easy to add
 * new beverage types without modifying client code.
 */
public class BeverageFactory {
    
    /**
     * Factory method to create beverages
     * @param type the type of beverage ("coffee", "tea", "juice")
     * @param size the size of the beverage
     * @param variant the specific variant (e.g., "Espresso" for coffee, "Green" for tea)
     * @return a new Beverage instance
     */
    public static Beverage createBeverage(String type, String size, String variant) {
        return switch (type.toLowerCase()) {
            case "coffee" -> new Coffee(size, variant);
            case "tea" -> new Tea(size, variant);
            case "juice" -> new Juice(size, variant);
            default -> throw new IllegalArgumentException("Unknown beverage type: " + type);
        };
    }
    
    /**
     * Convenience method to create a coffee
     */
    public static Beverage createCoffee(String size, String coffeeType) {
        return new Coffee(size, coffeeType);
    }
    
    /**
     * Convenience method to create a tea
     */
    public static Beverage createTea(String size, String teaType) {
        return new Tea(size, teaType);
    }
    
    /**
     * Convenience method to create a juice
     */
    public static Beverage createJuice(String size, String fruit) {
        return new Juice(size, fruit);
    }
}
