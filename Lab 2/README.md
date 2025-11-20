# Lab 2 - Structural Design Patterns

**Course:** Tehnici si Metode de Proiectare Software  
**Student:** Caraman Mihai  
**Group:** FAF 233  
**Date:** November 2025

## Project Overview

This project demonstrates **Structural Design Patterns** through a practical Restaurant Food Ordering System implemented in Java. Building upon the Creational Patterns from Lab 1, this project extends the system to show how classes and objects can be composed to form larger, more flexible structures.

### Implemented Patterns:

1. **Decorator Pattern** - Dynamic Pizza Customization
2. **Adapter Pattern** - Payment System Integration
3. **Facade Pattern** - Order Management System

## Domain Description

The project extends the **Restaurant Food Ordering System** with new capabilities:
- **Dynamic Pizza Customization**: Customers can add any combination of toppings (Cheese, Pepperoni, Mushrooms, etc.) to a base pizza without creating new classes for every combination.
- **Payment Integration**: The system can process payments through multiple external providers (PayPal, Stripe, Cash) using a unified interface.
- **Order Management**: A simplified interface coordinates the complex process of creating orders, notifying the kitchen, updating inventory, and processing payments.

This domain provides rich opportunities to demonstrate structural patterns:
- **Object Composition**: Stacking multiple toppings on a pizza.
- **Interface Adaptation**: Making incompatible payment APIs work with our system.
- **Subsystem Simplification**: Hiding the complexity of the ordering workflow.

## Project Structure

```
src/
├── main/java/md/utm/tmps/lab2/
│   ├── client/
│   │   └── Main.java                     # Main demonstration class
│   └── domain/
│       ├── decorators/                   # Decorator Pattern
│       │   ├── BaconDecorator.java
│       │   ├── CheeseDecorator.java
│       │   ├── MushroomDecorator.java
│       │   ├── OliveDecorator.java
│       │   ├── PepperoniDecorator.java
│       │   └── VegetableDecorator.java
│       ├── facade/                       # Facade Pattern
│       │   └── OrderManagementFacade.java
│       ├── models/
│       │   ├── BasePizza.java
│       │   ├── Order.java
│       │   ├── PizzaComponent.java       # Component Interface
│       │   └── ToppingDecorator.java     # Abstract Decorator
│       ├── payment/                      # Adapter Pattern
│       │   ├── PaymentProcessor.java     # Target Interface
│       │   ├── adapters/
│       │   │   ├── CashAdapter.java
│       │   │   ├── PayPalAdapter.java
│       │   │   └── StripeAdapter.java
│       │   └── external/                 # Adaptees (External APIs)
│       │       ├── CashRegisterSystem.java
│       │       ├── PayPalAPI.java
│       │       └── StripeAPI.java
│       └── subsystems/                   # Facade Subsystems
│           ├── InventoryManager.java
│           ├── KitchenService.java
│           ├── NotificationService.java
│           └── OrderIdGenerator.java
└── test/java/md/utm/tmps/lab2/
    └── StructuralPatternsTest.java       # Comprehensive test suite
```

## Structural Design Patterns Explanation

---

### 1. Decorator Pattern

> [!definition]
> **"Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality."**

**Implementation: ToppingDecorator**

The system uses the Decorator pattern to allow customers to add various toppings to a pizza. Instead of creating subclasses like `CheesePepperoniPizza`, we wrap the pizza object with decorators.

**Code Example:**

```java
// Component Interface
public interface PizzaComponent {
    String getDescription();
    double getCost();
    void prepare();
}

// Concrete Component
public class BasePizza implements PizzaComponent {
    private final String size;
    private final double basePrice;
    
    public BasePizza(String size, String crustType) {
        this.size = size;
        this.basePrice = switch (size.toLowerCase()) {
            case "small" -> 8.99;
            case "medium" -> 10.99;
            case "large" -> 12.99;
            default -> 10.99;
        };
    }
    
    @Override
    public double getCost() { return basePrice; }
    
    @Override
    public String getDescription() { return size + " pizza"; }
}

// Abstract Decorator
public abstract class ToppingDecorator implements PizzaComponent {
    protected PizzaComponent pizza;
    
    public ToppingDecorator(PizzaComponent pizza) {
        this.pizza = pizza;
    }
    
    @Override
    public String getDescription() { return pizza.getDescription(); }
    
    @Override
    public double getCost() { return pizza.getCost(); }
}

// Concrete Decorator
public class CheeseDecorator extends ToppingDecorator {
    public CheeseDecorator(PizzaComponent pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Mozzarella Cheese";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 1.50; // Add cost of cheese
    }
}

// Usage
PizzaComponent pizza = new BasePizza("Large", "Thin");
pizza = new CheeseDecorator(pizza);
pizza = new PepperoniDecorator(pizza);
System.out.println(pizza.getDescription()); // "Large pizza, Mozzarella Cheese, Pepperoni"
```

**Key Features:**
- **Transparent Enclosure**: Decorators implement the same interface as the component they wrap.
- **Recursive Composition**: Decorators can be nested to add multiple behaviors.
- **Dynamic Extension**: Functionality is added at runtime, not compile time.

**Benefits:**
- Avoids class explosion (no need for `CheesePepperoniMushroomPizza` class).
- Adheres to Open/Closed Principle (new toppings can be added without changing existing code).
- Flexible configuration of objects.

---

### 2. Adapter Pattern

> [!definition]
> **"Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces."**

**Implementation: PaymentProcessor Adapters**

The system needs to support PayPal, Stripe, and Cash. Each has a completely different API. The Adapter pattern unifies them under the `PaymentProcessor` interface.

**Code Example:**

```java
// Target Interface
public interface PaymentProcessor {
    boolean processPayment(double amount, String accountInfo);
}

// Adaptee (External PayPal API)
public class PayPalAPI {
    public boolean sendPayment(String email, double amount) {
        System.out.println("PayPal: Sending $" + amount + " to " + email);
        return true;
    }
}

// Adapter
public class PayPalAdapter implements PaymentProcessor {
    private final PayPalAPI payPalAPI;
    
    public PayPalAdapter() {
        this.payPalAPI = new PayPalAPI();
    }
    
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        // Adapt the call to the specific API method
        return payPalAPI.sendPayment(accountInfo, amount);
    }
}

// Usage
PaymentProcessor payment = new PayPalAdapter();
payment.processPayment(25.50, "user@example.com");
```

**Key Features:**
- **Target Interface**: Defines the domain-specific interface used by the client.
- **Adaptee**: The existing incompatible class (e.g., 3rd party library).
- **Adapter**: Implements the Target interface and delegates to the Adaptee.

**Benefits:**
- Decouples client code from concrete 3rd party implementations.
- Allows integration of new payment providers without changing client logic.
- Standardizes error handling and data formats across different systems.

---

### 3. Facade Pattern

> [!definition]
> **"Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use."**

**Implementation: OrderManagementFacade**

Creating and processing an order involves interacting with Inventory, Kitchen, Notifications, and Payment systems. The Facade hides this complexity.

**Code Example:**

```java
public class OrderManagementFacade {
    private final InventoryManager inventory;
    private final KitchenService kitchen;
    private final NotificationService notifications;
    private final OrderIdGenerator idGenerator;
    
    public OrderManagementFacade() {
        this.inventory = new InventoryManager();
        this.kitchen = new KitchenService();
        this.notifications = new NotificationService();
        this.idGenerator = OrderIdGenerator.getInstance();
    }
    
    // Simplified interface for the client
    public void processOrder(PizzaComponent pizza, String customerEmail, PaymentProcessor payment) {
        int orderId = idGenerator.generateOrderId();
        Order order = new Order(orderId);
        
        // Coordinate subsystems
        inventory.checkIngredients(pizza);
        double cost = pizza.getCost();
        
        if (payment.processPayment(cost, customerEmail)) {
            kitchen.prepareOrder(pizza);
            inventory.updateStock(pizza);
            notifications.sendOrderConfirmation(customerEmail, orderId);
        }
    }
}

// Usage - Client code is clean and simple
OrderManagementFacade facade = new OrderManagementFacade();
facade.processOrder(myPizza, "client@email.com", new PayPalAdapter());
```

**Key Features:**
- **Unified Interface**: Single entry point for a complex subsystem.
- **Decoupling**: Clients don't need to know about `InventoryManager` or `KitchenService`.
- **Coordination**: Manages the sequence of operations between subsystems.

**Benefits:**
- Reduces dependencies between client and complex subsystems.
- Simplifies the learning curve for using the system.
- Centralizes the logic for a specific workflow (ordering).

---

## How Patterns Work Together

The patterns interact to create a robust system:

```java
public class RestaurantDemo {
    public static void main(String[] args) {
        // 1. Decorator: Build a complex product
        PizzaComponent pizza = new BasePizza("Large", "Thin");
        pizza = new CheeseDecorator(pizza);
        pizza = new PepperoniDecorator(pizza);
        
        // 2. Adapter: Select a payment method
        PaymentProcessor payment = new StripeAdapter();
        
        // 3. Facade: Process the entire transaction simply
        OrderManagementFacade facade = new OrderManagementFacade();
        facade.createAndProcessOrder(pizza, "user@example.com", payment, "tok_visa");
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
# Navigate to Lab 2 directory
cd "Lab 2"

# Compile the project
mvn clean compile

# Run the main demonstration
mvn exec:java -Dexec.mainClass="md.utm.tmps.lab2.client.Main"

# Run tests
mvn test
```

### Expected Output

The Main class produces formatted output demonstrating each pattern:

```
╔════════════════════════════════════════════════════════════╗
║     STRUCTURAL DESIGN PATTERNS DEMONSTRATION               ║
║     Lab 2 - TMPS Course                                    ║
╚════════════════════════════════════════════════════════════╝

╔════════════════════════════════════════════════════════════╗
║ PATTERN 1: DECORATOR - Dynamic Pizza Customization        ║
╚════════════════════════════════════════════════════════════╝
Description: Large Thick crust pizza, Cheddar cheese, pepperoni, crispy bacon
Cost: $19.24

╔════════════════════════════════════════════════════════════╗
║ PATTERN 2: ADAPTER - Payment System Integration           ║
╚════════════════════════════════════════════════════════════╝
PayPal Adapter: Converting request for PayPal Payment Gateway
  → PayPal: Payment authorized by PayPal
✓ Payment completed successfully

╔════════════════════════════════════════════════════════════╗
║ PATTERN 3: FACADE - Order Management Workflow             ║
╚════════════════════════════════════════════════════════════╝
  → Inventory: Order #1000 registered
  → Notification: Ready notification sent to customer@email.com
✓ Order #1000 completed
```

---

## Test Results

The project includes comprehensive JUnit 5 tests covering:

1. **Decorator Tests**: Verifies cost calculation and description concatenation for various topping combinations.
2. **Adapter Tests**: Ensures all adapters correctly translate calls to their respective external APIs.
3. **Facade Tests**: Validates that the facade correctly coordinates the subsystems and handles the order workflow.

### Running Tests

```bash
mvn test
```

### Expected Test Output

```
[INFO] Running md.utm.tmps.lab2.StructuralPatternsTest
✓ Decorator test passed - Cost and description correct!
✓ Adapter test passed - Payments processed via different APIs!
✓ Facade test passed - Order workflow coordinated successfully!
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
```

---

## Key Observations

### Pattern Selection Rationale

1. **Decorator for Pizza**:
   - **Why**: Pizzas have exponential combinations of toppings. Inheritance is impractical.
   - **Result**: We can create any pizza configuration at runtime.

2. **Adapter for Payments**:
   - **Why**: We need to use existing 3rd party libraries (PayPal, Stripe) that we cannot modify and that have different interfaces.
   - **Result**: The application core is independent of specific payment providers.

3. **Facade for Orders**:
   - **Why**: The ordering process requires strict coordination of Inventory, Kitchen, and Notification services.
   - **Result**: The client code is clean and doesn't need to manage the complex dependencies.

### Design Principles Applied

- **Single Responsibility Principle**: Decorators only handle their specific topping; Adapters only handle translation.
- **Open/Closed Principle**: New toppings or payment methods can be added without modifying existing code.
- **Dependency Inversion**: The Facade and Client depend on the `PaymentProcessor` interface, not concrete implementations.

---

## Conclusion

This project demonstrates how Structural Design Patterns solve problems related to object composition and interface compatibility:

- **Decorator** provides a flexible alternative to subclassing for extending functionality.
- **Adapter** enables integration of incompatible classes.
- **Facade** simplifies the usage of complex systems.

By applying these patterns, the Restaurant Ordering System becomes more modular, extensible, and easier to maintain.

---

## References

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*
- Bloch, J. (2018). *Effective Java* (3rd ed.)
- Freeman, E., & Robson, E. (2020). *Head First Design Patterns* (2nd ed.)
