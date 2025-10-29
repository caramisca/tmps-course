package md.utm.tmps.lab1.client;

import md.utm.tmps.lab1.domain.models.*;
import md.utm.tmps.lab1.domain.factory.*;

/**
 * Main demonstration class for Creational Design Patterns
 * 
 * This class demonstrates:
 * 1. Singleton Pattern - OrderIdGenerator
 * 2. Builder Pattern - Pizza construction
 * 3. Factory Method Pattern - Beverage creation
 * 4. Abstract Factory Pattern - Meal creation
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   CREATIONAL DESIGN PATTERNS DEMONSTRATION                 ║");
        System.out.println("║   Domain: Restaurant Food Ordering System                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Demonstrate Singleton Pattern
        demonstrateSingleton();
        
        // Demonstrate Builder Pattern
        demonstrateBuilder();
        
        // Demonstrate Factory Method Pattern
        demonstrateFactoryMethod();
        
        // Demonstrate Abstract Factory Pattern
        demonstrateAbstractFactory();
        
        // Create complete orders using all patterns
        demonstrateCompleteOrder();
    }
    
    private static void demonstrateSingleton() {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. SINGLETON PATTERN - OrderIdGenerator                  │");
        System.out.println("└──────────────────────────────────────────────────────────┘");
        System.out.println("Purpose: Ensure only ONE instance manages order IDs\n");
        
        // Get the singleton instance
        OrderIdGenerator generator1 = OrderIdGenerator.getInstance();
        OrderIdGenerator generator2 = OrderIdGenerator.getInstance();
        
        System.out.println("✓ Getting OrderIdGenerator instances...");
        System.out.println("  Instance 1: " + generator1);
        System.out.println("  Instance 2: " + generator2);
        System.out.println("  Same instance? " + (generator1 == generator2));
        
        System.out.println("\n✓ Generating unique order IDs:");
        System.out.println("  Order ID 1: " + generator1.generateOrderId());
        System.out.println("  Order ID 2: " + generator2.generateOrderId());
        System.out.println("  Order ID 3: " + generator1.generateOrderId());
        
        System.out.println("\n✓ Singleton ensures no ID conflicts across the system!");
    }
    
    private static void demonstrateBuilder() {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│ 2. BUILDER PATTERN - Pizza Construction                  │");
        System.out.println("└──────────────────────────────────────────────────────────┘");
        System.out.println("Purpose: Build complex Pizza objects with many options\n");
        
        // Build a simple pizza
        Pizza margherita = new Pizza.Builder("Medium", "Thin")
                .cheese(true)
                .build();
        
        System.out.println("✓ Simple Margherita Pizza:");
        System.out.println("  " + margherita);
        
        // Build a complex pizza with many toppings
        Pizza supremePizza = new Pizza.Builder("Large", "Thick")
                .cheese(true)
                .pepperoni(true)
                .mushrooms(true)
                .olives(true)
                .bacon(true)
                .onions(true)
                .addExtraTopping("Green Peppers")
                .addExtraTopping("Pineapple")
                .build();
        
        System.out.println("\n✓ Supreme Pizza with custom toppings:");
        System.out.println("  " + supremePizza);
        
        System.out.println("\n✓ Builder pattern avoids constructor pollution!");
        System.out.println("  (No need for Pizza(size, crust, cheese, pepperoni, ...))");
    }
    
    private static void demonstrateFactoryMethod() {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│ 3. FACTORY METHOD PATTERN - Beverage Creation            │");
        System.out.println("└──────────────────────────────────────────────────────────┘");
        System.out.println("Purpose: Create beverages without exposing creation logic\n");
        
        // Create beverages using factory
        Beverage espresso = BeverageFactory.createCoffee("Small", "Espresso");
        Beverage greenTea = BeverageFactory.createTea("Medium", "Green");
        Beverage orangeJuice = BeverageFactory.createJuice("Large", "Orange");
        
        System.out.println("✓ Created beverages using Factory Method:");
        System.out.println("  " + espresso);
        System.out.println("  " + greenTea);
        System.out.println("  " + orangeJuice);
        
        System.out.println("\n✓ Preparing beverages:");
        espresso.prepare();
        System.out.println();
        greenTea.prepare();
        
        System.out.println("\n✓ Factory encapsulates object creation logic!");
    }
    
    private static void demonstrateAbstractFactory() {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│ 4. ABSTRACT FACTORY PATTERN - Meal Combos                │");
        System.out.println("└──────────────────────────────────────────────────────────┘");
        System.out.println("Purpose: Create families of related objects (Pizza + Beverage)\n");
        
        // Create different meal types using different factories
        MealFactory vegetarianFactory = new VegetarianMealFactory();
        MealFactory meatLoversFactory = new MeatLoversMealFactory();
        MealFactory italianFactory = new ItalianMealFactory();
        
        Meal vegMeal = vegetarianFactory.createMeal();
        Meal meatMeal = meatLoversFactory.createMeal();
        Meal italianMeal = italianFactory.createMeal();
        
        System.out.println("✓ Vegetarian Meal:");
        System.out.println("  " + vegMeal.getPizza());
        System.out.println("  " + vegMeal.getBeverage());
        
        System.out.println("\n✓ Meat Lovers Meal:");
        System.out.println("  " + meatMeal.getPizza());
        System.out.println("  " + meatMeal.getBeverage());
        
        System.out.println("\n✓ Italian Classic Meal:");
        System.out.println("  " + italianMeal.getPizza());
        System.out.println("  " + italianMeal.getBeverage());
        
        System.out.println("\n✓ Abstract Factory creates coordinated product families!");
    }
    
    private static void demonstrateCompleteOrder() {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│ COMPLETE ORDER - Using All Patterns Together             │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");
        
        // Create two orders (Singleton generates unique IDs)
        Order order1 = new Order();
        Order order2 = new Order();
        
        // Order 1: Custom items using Builder and Factory Method
        Pizza customPizza = new Pizza.Builder("Large", "Regular")
                .cheese(true)
                .pepperoni(true)
                .mushrooms(true)
                .build();
        
        Beverage coke = BeverageFactory.createBeverage("coffee", "Medium", "Latte");
        
        order1.addPizza(customPizza);
        order1.addBeverage(coke);
        
        // Order 2: Meal combo using Abstract Factory
        MealFactory factory = new VegetarianMealFactory();
        Meal meal = factory.createMeal();
        order2.addMeal(meal);
        
        System.out.println(order1);
        System.out.println("\n" + order2);
        
        System.out.println("\n✓ All creational patterns working together seamlessly!");
    }
}
