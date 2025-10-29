# Lab 1 - Creational Design Patterns

**Course:** Tehnici si Metode de Proiectare Software  
**Student:** Caraman Mihai  
**Group:** FAF 233  
**Date:** October 2025

## Project Overview

This project demonstrates **Creational Design Patterns** through a practical Restaurant Food Ordering System implemented in Java. The system showcases how different creational patterns solve object instantiation challenges in a real-world domain.

### Implemented Patterns:

1. **Singleton Pattern** - OrderIdGenerator
2. **Builder Pattern** - Pizza construction
3. **Factory Method Pattern** - Beverage creation
4. **Abstract Factory Pattern** - Meal combo creation

## Domain Description

The project simulates a **Restaurant Food Ordering System** where:
- Customers can order **Pizzas** with customizable toppings
- Customers can order **Beverages** (Coffee, Tea, Juice)
- Customers can order pre-configured **Meal Combos** (Pizza + Beverage)
- Each **Order** gets a unique ID from a centralized generator

This domain provides rich opportunities to demonstrate creational patterns:
- **Complex object construction** (Pizza with many optional toppings)
- **Family of related products** (Meal combos with coordinated items)
- **Controlled instantiation** (Unique order ID generation)
- **Encapsulated creation logic** (Different beverage types)

## Project Structure

```
src/
├── main/java/md/utm/tmps/lab1/
│   ├── client/
│   │   └── Main.java                    # Main demonstration class
│   └── domain/
│       ├── factory/
│       │   ├── BeverageFactory.java     # Factory Method implementation
│       │   ├── MealFactory.java         # Abstract Factory interface
│       │   ├── VegetarianMealFactory.java
│       │   ├── MeatLoversMealFactory.java
│       │   └── ItalianMealFactory.java
│       └── models/
│           ├── OrderIdGenerator.java    # Singleton implementation
│           ├── Pizza.java               # Builder implementation
│           ├── Beverage.java            # Abstract beverage
│           ├── Coffee.java
│           ├── Tea.java
│           ├── Juice.java
│           ├── Meal.java
│           └── Order.java
└── test/java/md/utm/tmps/lab1/
    └── CreationalPatternsTest.java      # Comprehensive test suite
```

## Creational Design Patterns Explanation

---

### 1. Singleton Pattern

> [!definition]
> **"Ensure a class has only one instance and provide a global point of access to it."**

**Implementation: OrderIdGenerator**

The `OrderIdGenerator` class ensures that all orders in the system receive unique, sequential IDs from a single source, preventing ID conflicts.

**Code Example:**

```java
public class OrderIdGenerator {
    // Single instance - volatile for thread visibility
    private static volatile OrderIdGenerator instance;
    private int nextOrderId;
    
    // Private constructor prevents direct instantiation
    private OrderIdGenerator() {
        this.nextOrderId = 1000;
    }
    
    // Double-checked locking for thread-safe lazy initialization
    public static OrderIdGenerator getInstance() {
        if (instance == null) {
            synchronized (OrderIdGenerator.class) {
                if (instance == null) {
                    instance = new OrderIdGenerator();
                }
            }
        }
        return instance;
    }
    
    public synchronized int generateOrderId() {
        return nextOrderId++;
    }
}

// Usage
OrderIdGenerator generator = OrderIdGenerator.getInstance();
int orderId = generator.generateOrderId(); // 1000
int orderId2 = generator.generateOrderId(); // 1001
```

**Key Features:**
- **Thread-safe**: Double-checked locking prevents race conditions
- **Lazy initialization**: Instance created only when first needed
- **Global access**: Single point of access throughout application

**Benefits:**
- Prevents duplicate order IDs
- Centralized control over ID generation
- Memory efficient (only one instance)
- Thread-safe for concurrent environments

---

### 2. Builder Pattern

> [!definition]
> **"Separate the construction of a complex object from its representation, allowing the same construction process to create different representations."**

**Implementation: Pizza.Builder**

The `Pizza` class uses the Builder pattern to handle complex pizza construction with many optional toppings, avoiding constructor pollution.

**Code Example:**

```java
public class Pizza {
    // Required parameters
    private final String size;
    private final String crustType;
    
    // Optional parameters
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;
    private final boolean olives;
    private final boolean bacon;
    private final boolean onions;
    private final List<String> extraToppings;
    
    // Private constructor - only Builder can create instances
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.crustType = builder.crustType;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        // ... copy other fields
    }
    
    public static class Builder {
        private final String size;
        private final String crustType;
        private boolean cheese = false;
        private boolean pepperoni = false;
        // ... other optional fields
        
        public Builder(String size, String crustType) {
            this.size = size;
            this.crustType = crustType;
        }
        
        public Builder cheese(boolean value) {
            this.cheese = value;
            return this;
        }
        
        public Builder pepperoni(boolean value) {
            this.pepperoni = value;
            return this;
        }
        
        // ... other builder methods
        
        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// Usage - Fluent interface for easy construction
Pizza margherita = new Pizza.Builder("Medium", "Thin")
        .cheese(true)
        .build();

Pizza supreme = new Pizza.Builder("Large", "Thick")
        .cheese(true)
        .pepperoni(true)
        .mushrooms(true)
        .olives(true)
        .bacon(true)
        .addExtraTopping("Green Peppers")
        .build();
```

**Key Features:**
- **Fluent interface**: Method chaining for readable code
- **Immutable objects**: All fields are final
- **Named parameters**: Clear what each option does
- **Flexible**: Easy to add/remove toppings

**Benefits:**
- Avoids telescoping constructors (constructors with many parameters)
- More readable than constructors with many parameters
- Objects are immutable and thread-safe
- Easy to add new optional parameters

---

### 3. Factory Method Pattern

> [!definition]
> **"Define an interface for creating an object, but let subclasses decide which class to instantiate."**

**Implementation: BeverageFactory**

The `BeverageFactory` provides static factory methods to create different types of beverages, encapsulating the instantiation logic.

**Code Example:**

```java
// Abstract base class
public abstract class Beverage {
    protected String name;
    protected String size;
    protected double price;
    
    public abstract void prepare();
    public abstract String getDescription();
}

// Concrete implementations
public class Coffee extends Beverage {
    private String coffeeType;
    
    public Coffee(String size, String coffeeType) {
        super("Coffee", size, calculatePrice(size));
        this.coffeeType = coffeeType;
    }
    
    @Override
    public void prepare() {
        System.out.println("Brewing " + coffeeType + " coffee...");
        System.out.println("Coffee is ready!");
    }
}

public class Tea extends Beverage {
    private String teaType;
    
    public Tea(String size, String teaType) {
        super("Tea", size, calculatePrice(size));
        this.teaType = teaType;
    }
    
    @Override
    public void prepare() {
        System.out.println("Steeping " + teaType + " tea leaves...");
        System.out.println("Tea is ready!");
    }
}

// Factory Method
public class BeverageFactory {
    public static Beverage createBeverage(String type, String size, String variant) {
        return switch (type.toLowerCase()) {
            case "coffee" -> new Coffee(size, variant);
            case "tea" -> new Tea(size, variant);
            case "juice" -> new Juice(size, variant);
            default -> throw new IllegalArgumentException("Unknown beverage: " + type);
        };
    }
    
    // Convenience methods
    public static Beverage createCoffee(String size, String coffeeType) {
        return new Coffee(size, coffeeType);
    }
    
    public static Beverage createTea(String size, String teaType) {
        return new Tea(size, teaType);
    }
}

// Usage - Client code doesn't know concrete classes
Beverage espresso = BeverageFactory.createCoffee("Small", "Espresso");
Beverage greenTea = BeverageFactory.createTea("Medium", "Green");
Beverage juice = BeverageFactory.createJuice("Large", "Orange");

// Polymorphic usage
espresso.prepare();
greenTea.prepare();
```

**Key Features:**
- **Encapsulation**: Creation logic hidden from client
- **Polymorphism**: Returns abstract `Beverage` type
- **Extensibility**: Easy to add new beverage types
- **Type safety**: Switch expression with exhaustive cases

**Benefits:**
- Decouples client code from concrete classes
- Centralized creation logic
- Easy to modify or extend beverage types
- Promotes programming to interfaces

---

### 4. Abstract Factory Pattern

> [!definition]
> **"Provide an interface for creating families of related or dependent objects without specifying their concrete classes."**

**Implementation: MealFactory**

The `MealFactory` creates complete meal combos where the Pizza and Beverage are designed to complement each other.

**Code Example:**

```java
// Meal represents a family of products
public class Meal {
    private final Pizza pizza;
    private final Beverage beverage;
    private final String mealType;
    
    public Meal(Pizza pizza, Beverage beverage, String mealType) {
        this.pizza = pizza;
        this.beverage = beverage;
        this.mealType = mealType;
    }
}

// Abstract Factory interface
public interface MealFactory {
    Meal createMeal();
    String getMealType();
}

// Concrete Factory 1: Vegetarian Meal
public class VegetarianMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {
        Pizza pizza = new Pizza.Builder("Medium", "Thin")
                .cheese(true)
                .mushrooms(true)
                .olives(true)
                .onions(true)
                .addExtraTopping("Tomatoes")
                .addExtraTopping("Bell Peppers")
                .build();
        
        Beverage beverage = BeverageFactory.createJuice("Medium", "Orange");
        
        return new Meal(pizza, beverage, "Vegetarian");
    }
    
    @Override
    public String getMealType() {
        return "Vegetarian";
    }
}

// Concrete Factory 2: Meat Lovers Meal
public class MeatLoversMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {
        Pizza pizza = new Pizza.Builder("Large", "Thick")
                .cheese(true)
                .pepperoni(true)
                .bacon(true)
                .addExtraTopping("Sausage")
                .addExtraTopping("Ham")
                .build();
        
        Beverage beverage = BeverageFactory.createCoffee("Large", "Espresso");
        
        return new Meal(pizza, beverage, "Meat Lovers");
    }
    
    @Override
    public String getMealType() {
        return "Meat Lovers";
    }
}

// Concrete Factory 3: Italian Classic Meal
public class ItalianMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {
        Pizza pizza = new Pizza.Builder("Medium", "Regular")
                .cheese(true)
                .mushrooms(true)
                .olives(true)
                .addExtraTopping("Fresh Basil")
                .build();
        
        Beverage beverage = BeverageFactory.createCoffee("Small", "Cappuccino");
        
        return new Meal(pizza, beverage, "Italian Classic");
    }
    
    @Override
    public String getMealType() {
        return "Italian Classic";
    }
}

// Usage - Easy to switch between meal types
MealFactory factory = new VegetarianMealFactory();
Meal meal = factory.createMeal();

// Or use a different factory
factory = new MeatLoversMealFactory();
meal = factory.createMeal();
```

**Key Features:**
- **Product families**: Pizza and Beverage are related products
- **Consistency**: Each factory creates compatible products
- **Extensibility**: Easy to add new meal types
- **Encapsulation**: Creation logic for entire families

**Benefits:**
- Ensures product compatibility within families
- Isolates concrete classes from client code
- Easy to exchange product families
- Promotes consistency among products

---

## How Patterns Work Together

The power of design patterns comes from combining them effectively:

```java
// Complete order using all patterns together
public class RestaurantDemo {
    public static void main(String[] args) {
        // 1. Singleton - Get unique order ID
        Order order = new Order(); // Uses OrderIdGenerator.getInstance()
        
        // 2. Builder - Create custom pizza
        Pizza customPizza = new Pizza.Builder("Large", "Regular")
                .cheese(true)
                .pepperoni(true)
                .mushrooms(true)
                .build();
        order.addPizza(customPizza);
        
        // 3. Factory Method - Create beverage
        Beverage coffee = BeverageFactory.createCoffee("Medium", "Latte");
        order.addBeverage(coffee);
        
        // 4. Abstract Factory - Add meal combo
        MealFactory mealFactory = new VegetarianMealFactory();
        Meal meal = mealFactory.createMeal();
        order.addMeal(meal);
        
        System.out.println(order);
    }
}
```

---

## Running the Project

### Prerequisites
- Java 21 (JDK 21)
- Maven 3.8+

### Build and Run

```bash
# Navigate to Lab 1 directory
cd "Lab 1"

# Compile the project
mvn clean compile

# Run the main demonstration
mvn exec:java -Dexec.mainClass="md.utm.tmps.lab1.client.Main"

# Run tests
mvn test
```

### Expected Output

The Main class produces formatted output demonstrating each pattern:

```
╔════════════════════════════════════════════════════════════╗
║   CREATIONAL DESIGN PATTERNS DEMONSTRATION                 ║
║   Domain: Restaurant Food Ordering System                  ║
╚════════════════════════════════════════════════════════════╝

┌──────────────────────────────────────────────────────────┐
│ 1. SINGLETON PATTERN - OrderIdGenerator                  │
└──────────────────────────────────────────────────────────┘
✓ Getting OrderIdGenerator instances...
  Same instance? true
✓ Generating unique order IDs:
  Order ID 1: 1000
  Order ID 2: 1001
  Order ID 3: 1002

┌──────────────────────────────────────────────────────────┐
│ 2. BUILDER PATTERN - Pizza Construction                  │
└──────────────────────────────────────────────────────────┘
✓ Simple Margherita Pizza:
  Pizza [Medium, Thin crust] - Toppings: Cheese

✓ Supreme Pizza with custom toppings:
  Pizza [Large, Thick crust] - Toppings: Cheese, Pepperoni, Mushrooms, 
  Olives, Bacon, Onions, Green Peppers, Pineapple

... (more output)
```

---

## Test Results

The project includes comprehensive JUnit 5 tests covering:

1. **Singleton Pattern Test**
   - Verifies single instance across application
   - Tests thread safety with concurrent access
   - Validates unique ID generation

2. **Builder Pattern Test**
   - Tests simple and complex pizza construction
   - Validates fluent interface
   - Checks immutability

3. **Factory Method Test**
   - Tests creation of all beverage types
   - Validates polymorphism
   - Tests error handling

4. **Abstract Factory Test**
   - Tests all meal factory implementations
   - Validates product family consistency
   - Checks coordinated object creation

5. **Integration Test**
   - Tests all patterns working together
   - Validates complete order flow
   - Tests pattern interactions

### Running Tests

```bash
mvn test
```

### Expected Test Output

```
[INFO] Running md.utm.tmps.lab1.CreationalPatternsTest
✓ Singleton test passed - Single instance manages all order IDs!
✓ Builder test passed - Pizzas built with flexible configuration!
✓ Factory Method test passed - Beverages created without exposing logic!
✓ Abstract Factory test passed - Meal families created consistently!
✓ Integration test passed - All patterns work together seamlessly!
✓ Thread safety test passed - Singleton is thread-safe!
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

---

## Key Observations

### Pattern Selection Rationale

1. **Singleton for OrderIdGenerator**
   - Need centralized ID generation
   - Prevents duplicate IDs across system
   - Thread-safe for concurrent orders

2. **Builder for Pizza**
   - Many optional toppings (8+ boolean fields + list)
   - Avoids constructor with 10+ parameters
   - Fluent interface improves readability

3. **Factory Method for Beverages**
   - Simple creation with type parameter
   - Encapsulates concrete class selection
   - Easy to extend with new types

4. **Abstract Factory for Meals**
   - Creates coordinated product families
   - Ensures Pizza + Beverage compatibility
   - Easy to add new meal themes

### Design Principles Applied

- **Open/Closed Principle**: Easy to add new beverage types or meal factories without modifying existing code
- **Single Responsibility**: Each factory has one clear purpose
- **Dependency Inversion**: Client code depends on abstractions (Beverage, MealFactory)
- **Liskov Substitution**: All beverages and factories are substitutable

---

## Conclusion

This project demonstrates how Creational Design Patterns solve real-world object creation challenges:

- **Singleton** ensures controlled instantiation and global access
- **Builder** handles complex object construction elegantly
- **Factory Method** encapsulates object creation logic
- **Abstract Factory** creates families of related objects

These patterns work together to create a flexible, maintainable, and extensible restaurant ordering system. Each pattern addresses a specific creation problem while following SOLID principles and promoting clean architecture.

---

## References

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*
- Bloch, J. (2018). *Effective Java* (3rd ed.)
- Freeman, E., & Robson, E. (2020). *Head First Design Patterns* (2nd ed.)

---
