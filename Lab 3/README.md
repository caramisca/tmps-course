# Lab 3 - Behavioral Design Patterns

**Course:** Tehnici si Metode de Proiectare Software  
**Student:** Caraman Mihai  
**Group:** FAF 233  
**Date:** November 2025

## Project Overview

This project demonstrates **Behavioral Design Patterns** through a practical Computer Components Store system implemented in Java. Building upon the foundation of previous labs, this project shows how objects interact and communicate with each other in flexible and maintainable ways.

### Implemented Patterns:

1. **Observer Pattern** - Order Status Notification System
2. **Strategy Pattern** - Shipping Cost Calculation Strategies
3. **Chain of Responsibility Pattern** - Order Validation Pipeline

## Domain Description

The project implements a **Computer Components Store Order Management System** with the following capabilities:

- **Order Status Tracking**: Customers and system administrators receive real-time notifications when order status changes (pending, processing, shipped, delivered).
- **Dynamic Shipping Calculation**: The system supports multiple shipping methods (Standard, Express, Overnight, Free) that can be selected and changed at runtime.
- **Order Validation Pipeline**: Before processing, orders go through a series of validation checks (non-empty, valid email, minimum value, inventory availability).

This domain provides rich opportunities to demonstrate behavioral patterns:
- **Communication**: How different parts of the system communicate order status changes.
- **Algorithm Selection**: How different shipping calculation strategies are applied dynamically.
- **Request Processing**: How validation requests flow through a chain of handlers.

## Project Structure

```
src/
├── main/java/md/utm/tmps/lab3/
│   ├── client/
│   │   └── Main.java                          # Main demonstration class
│   └── domain/
│       ├── models/                            # Domain Models
│       │   ├── Component.java                 # Base component class
│       │   ├── CPU.java                       # CPU component
│       │   ├── GPU.java                       # GPU component
│       │   ├── RAM.java                       # RAM component
│       │   ├── Storage.java                   # Storage component
│       │   ├── Order.java                     # Order entity
│       │   └── OrderStatus.java               # Order status enum
│       ├── observer/                          # Observer Pattern
│       │   ├── OrderObserver.java             # Observer interface
│       │   ├── OrderSubject.java              # Subject interface
│       │   ├── OrderTracker.java              # Concrete subject
│       │   ├── EmailNotifier.java             # Email observer
│       │   ├── SMSNotifier.java               # SMS observer
│       │   └── LoggingObserver.java           # Logging observer
│       ├── strategy/                          # Strategy Pattern
│       │   ├── ShippingStrategy.java          # Strategy interface
│       │   ├── StandardShippingStrategy.java  # Standard shipping
│       │   ├── ExpressShippingStrategy.java   # Express shipping
│       │   ├── OvernightShippingStrategy.java # Overnight shipping
│       │   ├── FreeShippingStrategy.java      # Free shipping
│       │   └── ShippingCalculator.java        # Context class
│       └── chain/                             # Chain of Responsibility
│           ├── OrderValidator.java            # Abstract handler
│           ├── ValidationResult.java          # Validation result
│           ├── EmptyOrderValidator.java       # Empty order check
│           ├── EmailValidator.java            # Email format check
│           ├── MinimumOrderValidator.java     # Minimum value check
│           ├── InventoryValidator.java        # Inventory check
│           └── ValidationChainBuilder.java    # Chain builder
└── test/java/md/utm/tmps/lab3/
    └── BehavioralPatternsTest.java            # Comprehensive test suite
```

## Behavioral Design Patterns Explanation

---

### 1. Observer Pattern

> **"Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically."**

**Implementation: Order Status Notification System**

The Observer pattern is used to notify multiple parties (customers, administrators, logging systems) when an order's status changes. This decouples the order tracking logic from the notification mechanisms.

**Code Example:**

```java
// Observer Interface
public interface OrderObserver {
    void onOrderStatusChanged(Order order, String message);
}

// Subject Interface
public interface OrderSubject {
    void attach(OrderObserver observer);
    void detach(OrderObserver observer);
    void notifyObservers(Order order, String message);
}

// Concrete Subject
public class OrderTracker implements OrderSubject {
    private final List<OrderObserver> observers;
    
    @Override
    public void notifyObservers(Order order, String message) {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(order, message);
        }
    }
    
    public void updateOrderStatus(Order order, OrderStatus newStatus, String message) {
        order.setStatus(newStatus);
        notifyObservers(order, message);
    }
}

// Concrete Observers
public class EmailNotifier implements OrderObserver {
    private final String recipientEmail;
    
    @Override
    public void onOrderStatusChanged(Order order, String message) {
        System.out.println("📧 Email to " + recipientEmail + ": " + message);
    }
}

public class SMSNotifier implements OrderObserver {
    private final String phoneNumber;
    
    @Override
    public void onOrderStatusChanged(Order order, String message) {
        System.out.println("📱 SMS to " + phoneNumber + ": " + message);
    }
}

// Usage
OrderTracker tracker = new OrderTracker();
tracker.attach(new EmailNotifier("customer@example.com"));
tracker.attach(new SMSNotifier("+1-555-0123"));
tracker.attach(new LoggingObserver());

tracker.updateOrderStatus(order, OrderStatus.SHIPPED, "Your order has shipped!");
// All three observers are notified automatically
```

**Key Features:**
- **Loose Coupling**: The subject doesn't need to know the concrete types of observers.
- **Dynamic Subscription**: Observers can be added or removed at runtime.
- **Broadcast Communication**: One event can trigger updates to multiple observers.

**Benefits:**
- Easy to add new notification channels without modifying existing code.
- The order tracking system is independent of notification mechanisms.
- Observers can be reused across different subjects.

**Real-World Application:**
In a production system, this pattern allows:
- Sending emails to customers
- Sending SMS notifications
- Logging to audit systems
- Updating dashboards in real-time
- Triggering webhook notifications to external systems

---

### 2. Strategy Pattern

> **"Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it."**

**Implementation: Shipping Cost Calculation**

The Strategy pattern allows the system to support multiple shipping methods (Standard, Express, Overnight, Free) with different cost calculations and delivery times. Customers can choose their preferred method, and the calculation logic is encapsulated in separate strategy classes.

**Code Example:**

```java
// Strategy Interface
public interface ShippingStrategy {
    double calculateShipping(Order order);
    String getStrategyName();
    int getEstimatedDeliveryDays();
}

// Concrete Strategies
public class StandardShippingStrategy implements ShippingStrategy {
    private static final double BASE_COST = 5.99;
    private static final double COST_PER_KG = 2.50;
    
    @Override
    public double calculateShipping(Order order) {
        return BASE_COST + (order.getTotalWeight() * COST_PER_KG);
    }
    
    @Override
    public String getStrategyName() {
        return "Standard Shipping";
    }
    
    @Override
    public int getEstimatedDeliveryDays() {
        return 5;
    }
}

public class ExpressShippingStrategy implements ShippingStrategy {
    private static final double BASE_COST = 15.99;
    private static final double COST_PER_KG = 4.00;
    
    @Override
    public double calculateShipping(Order order) {
        return BASE_COST + (order.getTotalWeight() * COST_PER_KG);
    }
    
    @Override
    public int getEstimatedDeliveryDays() {
        return 2;
    }
}

public class FreeShippingStrategy implements ShippingStrategy {
    private static final double MINIMUM_ORDER_VALUE = 500.00;
    
    @Override
    public double calculateShipping(Order order) {
        if (order.getTotalPrice() >= MINIMUM_ORDER_VALUE) {
            return 0.0;
        }
        return new StandardShippingStrategy().calculateShipping(order);
    }
}

// Context Class
public class ShippingCalculator {
    private ShippingStrategy strategy;
    
    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public double calculateShipping(Order order) {
        double cost = strategy.calculateShipping(order);
        order.setShippingCost(cost);
        return cost;
    }
}

// Usage
ShippingCalculator calculator = new ShippingCalculator(new StandardShippingStrategy());
double cost = calculator.calculateShipping(order);

// Change strategy at runtime
calculator.setStrategy(new ExpressShippingStrategy());
cost = calculator.calculateShipping(order);

// Customer wants fastest delivery
calculator.setStrategy(new OvernightShippingStrategy());
cost = calculator.calculateShipping(order);
```

**Key Features:**
- **Encapsulation of Algorithms**: Each shipping method is a separate class.
- **Runtime Selection**: Strategy can be changed dynamically based on user choice.
- **Open/Closed Principle**: New strategies can be added without modifying existing code.

**Benefits:**
- Eliminates complex conditional statements for different shipping methods.
- Each strategy is independently testable.
- Easy to add new shipping options (e.g., SameDayShipping, InternationalShipping).

**Comparison of Strategies:**

| Strategy | Base Cost | Per-KG Cost | Delivery Time | Best For |
|----------|-----------|-------------|---------------|----------|
| Standard | $5.99 | $2.50 | 5 days | Budget-conscious customers |
| Express | $15.99 | $4.00 | 2 days | Balanced speed/cost |
| Overnight | $29.99 | $6.50 | 1 day | Urgent orders |
| Free | $0 | $0 | 7 days | Orders over $500 |

---

### 3. Chain of Responsibility Pattern

> **"Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it."**

**Implementation: Order Validation Pipeline**

The Chain of Responsibility pattern creates a pipeline of validators that check different aspects of an order. If any validator fails, the chain stops and returns the error. This allows for flexible validation workflows.

**Code Example:**

```java
// Abstract Handler
public abstract class OrderValidator {
    protected OrderValidator nextValidator;
    
    public OrderValidator setNext(OrderValidator validator) {
        this.nextValidator = validator;
        return validator;
    }
    
    public abstract ValidationResult validate(Order order);
    
    protected ValidationResult passToNext(Order order) {
        if (nextValidator != null) {
            return nextValidator.validate(order);
        }
        return ValidationResult.success("All validations passed");
    }
}

// Validation Result
public class ValidationResult {
    private final boolean isValid;
    private final String message;
    
    public static ValidationResult success(String message) {
        return new ValidationResult(true, message);
    }
    
    public static ValidationResult failure(String message) {
        return new ValidationResult(false, message);
    }
}

// Concrete Handlers
public class EmptyOrderValidator extends OrderValidator {
    @Override
    public ValidationResult validate(Order order) {
        if (order.getComponents().isEmpty()) {
            return ValidationResult.failure("Order is empty");
        }
        return passToNext(order);
    }
}

public class EmailValidator extends OrderValidator {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    @Override
    public ValidationResult validate(Order order) {
        if (!EMAIL_PATTERN.matcher(order.getCustomerEmail()).matches()) {
            return ValidationResult.failure("Invalid email format");
        }
        return passToNext(order);
    }
}

public class MinimumOrderValidator extends OrderValidator {
    private static final double MINIMUM_ORDER_VALUE = 50.00;
    
    @Override
    public ValidationResult validate(Order order) {
        if (order.getTotalPrice() < MINIMUM_ORDER_VALUE) {
            return ValidationResult.failure("Order below minimum value");
        }
        return passToNext(order);
    }
}

public class InventoryValidator extends OrderValidator {
    @Override
    public ValidationResult validate(Order order) {
        // Check if all components are in stock
        for (Component component : order.getComponents()) {
            if (!isInStock(component)) {
                return ValidationResult.failure("Component out of stock: " + component.getName());
            }
        }
        return passToNext(order);
    }
}

// Chain Builder
public class ValidationChainBuilder {
    public static OrderValidator buildStandardChain() {
        OrderValidator emptyCheck = new EmptyOrderValidator();
        OrderValidator emailCheck = new EmailValidator();
        OrderValidator minimumCheck = new MinimumOrderValidator();
        OrderValidator inventoryCheck = new InventoryValidator();
        
        emptyCheck.setNext(emailCheck)
                  .setNext(minimumCheck)
                  .setNext(inventoryCheck);
        
        return emptyCheck;
    }
}

// Usage
OrderValidator validationChain = ValidationChainBuilder.buildStandardChain();
ValidationResult result = validationChain.validate(order);

if (!result.isValid()) {
    System.out.println("Validation failed: " + result.getMessage());
    return;
}

// Order passed all validations, proceed with processing
processOrder(order);
```

**Key Features:**
- **Sequential Processing**: Request flows through handlers in a specific order.
- **Early Termination**: Chain stops at first failure, avoiding unnecessary checks.
- **Flexible Composition**: Handlers can be added, removed, or reordered easily.

**Benefits:**
- Separates validation concerns into individual classes (Single Responsibility).
- Easy to add new validation rules without modifying existing validators.
- Validation order can be customized for different scenarios.
- Each validator is independently testable.

**Validation Flow:**

```
Order  EmptyOrderValidator  EmailValidator  MinimumOrderValidator  InventoryValidator  Success
           ↓ Fail                ↓ Fail           ↓ Fail                  ↓ Fail
        Return Error         Return Error     Return Error            Return Error
```

**Custom Chains:**

You can build custom validation chains for different scenarios:

```java
// Quick validation for returning customers
OrderValidator quickChain = ValidationChainBuilder.buildCustomChain(
    new EmptyOrderValidator(),
    new InventoryValidator()
);

// Strict validation for high-value orders
OrderValidator strictChain = ValidationChainBuilder.buildCustomChain(
    new EmptyOrderValidator(),
    new EmailValidator(),
    new PhoneValidator(),
    new MinimumOrderValidator(),
    new CreditCheckValidator(),
    new InventoryValidator(),
    new FraudDetectionValidator()
);
```

---

## How Patterns Work Together

The three patterns collaborate to create a complete order processing system:

```java
public class IntegratedOrderProcessing {
    public static void processOrder(Order order) {
        // 1. CHAIN OF RESPONSIBILITY: Validate the order
        OrderValidator validator = ValidationChainBuilder.buildStandardChain();
        ValidationResult validation = validator.validate(order);
        
        if (!validation.isValid()) {
            System.out.println("❌ Order validation failed: " + validation.getMessage());
            return;
        }
        
        // 2. STRATEGY: Calculate shipping based on customer preference
        ShippingCalculator calculator = new ShippingCalculator(
            new ExpressShippingStrategy() // Customer selected Express
        );
        calculator.calculateShipping(order);
        
        // 3. OBSERVER: Set up notifications
        OrderTracker tracker = new OrderTracker();
        tracker.attach(new EmailNotifier(order.getCustomerEmail()));
        tracker.attach(new SMSNotifier(customer.getPhone()));
        tracker.attach(new LoggingObserver());
        
        // Process the order and notify observers
        tracker.updateOrderStatus(order, OrderStatus.PROCESSING, "Payment received");
        
        // ... prepare components ...
        
        tracker.updateOrderStatus(order, OrderStatus.SHIPPED, "Package handed to carrier");
        
        // All observers automatically notified!
    }
}
```

**Pattern Interaction:**
1. **Chain of Responsibility** ensures only valid orders are processed.
2. **Strategy** calculates the correct shipping cost based on the chosen method.
3. **Observer** keeps all stakeholders informed throughout the process.

---

## Running the Project

### Prerequisites
- Java 21 (JDK 21)
- Maven 3.8+

### Build and Run

#### Windows (PowerShell)
```powershell
# Navigate to Lab 3 directory
cd "Lab 3"

# Compile the project
mvn clean compile

# Run the main demonstration
mvn exec:java -Dexec.mainClass="md.utm.tmps.lab3.client.Main"

# Run tests
mvn test

# Or use the provided script
.\run.ps1
```

#### Windows (Batch)
```batch
run.bat
```

### Expected Output

The Main class produces formatted output demonstrating each pattern:

```
╔════════════════════════════════════════════════════════════════════╗
║          BEHAVIORAL DESIGN PATTERNS DEMONSTRATION                  ║
║                 Lab 3 - TMPS Course                                ║
║              Computer Components Store                             ║
╚════════════════════════════════════════════════════════════════════╝

╔════════════════════════════════════════════════════════════════════╗
║ PATTERN 1: OBSERVER - Order Status Notifications                  ║
╚════════════════════════════════════════════════════════════════════╝

   Observer attached to order tracker
   Observer attached to order tracker
   Observer attached to order tracker

Created order: Order #1001 [PENDING] - 2 items - $2189.98

Updating order status (observers will be notified):
  📧 Email Notification to customer@example.com:
     Subject: Order #1001 Update
     Message: Order #1001 status changed: PENDING  VALIDATED. Order validated successfully
  📱 SMS Notification to +1-555-0123:
     Order #1001 status changed: PENDING  VALIDATED. Order validated successfully
  📝 [LOG 2025-11-25 14:30:45] Order #1001 status changed: PENDING  VALIDATED. Order validated successfully

...

╔════════════════════════════════════════════════════════════════════╗
║ PATTERN 2: STRATEGY - Shipping Cost Calculation                   ║
╚════════════════════════════════════════════════════════════════════╝

Order details:
  Items: 3
  Total: $939.97
  Weight: 1.5 kg

Testing different shipping strategies:

1. Standard Shipping:
   Standard Shipping (Est. 5 days)
   Cost: $9.74

2. Express Shipping:
   Express Shipping (Est. 2 days)
   Cost: $21.99

3. Overnight Shipping:
   Overnight Shipping (Est. 1 day)
   Cost: $39.74

...

╔════════════════════════════════════════════════════════════════════╗
║ PATTERN 3: CHAIN OF RESPONSIBILITY - Order Validation             ║
╚════════════════════════════════════════════════════════════════════╝

Validation Chain:
  Empty Order Check  Email Check  Minimum Value Check  Inventory Check

Test 1: Valid Order
   Checking if order has items...
     Order has 1 items
   Validating customer email...
     Email is valid
   Checking minimum order value...
     Order value is above minimum
   Checking inventory availability...
     All components are in stock
Result:  All validations passed

...
```

---

## Test Results

The project includes a comprehensive JUnit 5 test suite with 22 tests covering:

1. **Observer Pattern Tests** (5 tests):
   - Attach and notify observers
   - Multiple observers receive notifications
   - Detach observer stops notifications
   - EmailNotifier and SMSNotifier formatting

2. **Strategy Pattern Tests** (6 tests):
   - All shipping strategies calculate correctly
   - Calculator changes strategies dynamically
   - Calculator updates order shipping cost
   - Free shipping minimum threshold

3. **Chain of Responsibility Tests** (7 tests):
   - Individual validators work correctly
   - Chain stops at first failure
   - Custom chains can be built
   - Validation chain builder creates proper chain

4. **Integration Tests** (2 tests):
   - Complete order workflow with all patterns
   - All patterns working together

5. **Model Tests** (2 tests):
   - Component models work correctly
   - Order calculations are accurate

### Running Tests

```bash
mvn test
```

### Expected Test Output

```
[INFO] Running md.utm.tmps.lab3.BehavioralPatternsTest

[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.234 s

[INFO] Results:
[INFO] 
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
```

---

## Key Observations

### Pattern Selection Rationale

1. **Observer for Notifications**:
   - **Why**: Multiple parties need to be notified of order status changes.
   - **Alternative Considered**: Direct method calls would create tight coupling.
   - **Result**: Easy to add new notification channels without modifying OrderTracker.

2. **Strategy for Shipping**:
   - **Why**: Multiple shipping calculation algorithms with different costs/speeds.
   - **Alternative Considered**: Switch statements would violate Open/Closed Principle.
   - **Result**: New shipping methods can be added as new strategy classes.

3. **Chain of Responsibility for Validation**:
   - **Why**: Multiple validation steps that should stop at first failure.
   - **Alternative Considered**: Nested if-statements would be hard to maintain.
   - **Result**: Validators can be added, removed, or reordered without affecting others.

### Design Principles Applied

- **Single Responsibility Principle**: Each observer, strategy, and validator has one reason to change.
- **Open/Closed Principle**: New observers, strategies, or validators can be added without modifying existing code.
- **Liskov Substitution Principle**: All concrete implementations can replace their abstractions.
- **Dependency Inversion Principle**: High-level modules depend on abstractions (interfaces), not concrete classes.

### Real-World Scalability

This implementation can be extended for production use:

**Observer Pattern Extensions:**
- WebhookNotifier for external system integration
- PushNotifier for mobile app notifications
- SlackNotifier for team collaboration
- DatabaseLogger for persistent audit trails

**Strategy Pattern Extensions:**
- InternationalShippingStrategy with customs fees
- SameDayShippingStrategy with location-based pricing
- PickupStrategy for in-store pickup (zero shipping cost)
- WeightTierStrategy with volume discounts

**Chain of Responsibility Extensions:**
- FraudDetectionValidator
- CreditCheckValidator for high-value orders
- AddressValidator with address verification API
- TaxCalculationValidator
- PromotionValidator for discount codes

---

## Comparison with Previous Labs

| Lab | Focus | Patterns | Purpose |
|-----|-------|----------|---------|
| Lab 1 | Creational | Factory, Singleton, Builder | Object creation |
| Lab 2 | Structural | Decorator, Adapter, Facade | Object composition |
| **Lab 3** | **Behavioral** | **Observer, Strategy, Chain of Responsibility** | **Object interaction** |

**Evolution of the System:**
- **Lab 1**: Created the basic objects (components, orders)
- **Lab 2**: Composed objects into larger structures (payment systems, order management)
- **Lab 3**: Defined how objects communicate and collaborate (notifications, algorithms, validation)

---

## Conclusion

This project demonstrates how Behavioral Design Patterns solve problems related to object communication and responsibility distribution:

- **Observer Pattern** enables loose coupling between objects that need to react to state changes.
- **Strategy Pattern** allows algorithms to be selected and changed at runtime without modifying client code.
- **Chain of Responsibility Pattern** distributes processing logic across a chain of handlers, allowing flexible request handling.

By applying these patterns, the Computer Components Store system becomes:
- **More Flexible**: Shipping methods and validators can be changed without code modification.
- **More Maintainable**: Each behavior is encapsulated in its own class.
- **More Scalable**: New observers, strategies, or validators can be added easily.
- **More Testable**: Each component can be tested in isolation.

The integration of all three patterns shows how behavioral patterns work together to create a robust, maintainable, and extensible system where objects collaborate effectively while remaining loosely coupled.

---

## References

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*
- Freeman, E., & Robson, E. (2020). *Head First Design Patterns* (2nd ed.)
- Bloch, J. (2018). *Effective Java* (3rd ed.)
- Martin, R. C. (2017). *Clean Architecture: A Craftsman's Guide to Software Structure and Design*
