package md.utm.tmps.lab3.client;

import md.utm.tmps.lab3.domain.models.*;
import md.utm.tmps.lab3.domain.observer.*;
import md.utm.tmps.lab3.domain.strategy.*;
import md.utm.tmps.lab3.domain.chain.*;

/**
 * Main client demonstrating all three behavioral design patterns
 * - Observer Pattern: Order status notifications
 * - Strategy Pattern: Shipping cost calculation
 * - Chain of Responsibility: Order validation
 */
public class Main {
    
    private static final String HEADER_LINE = "╔" + "═".repeat(68) + "╗";
    private static final String FOOTER_LINE = "╚" + "═".repeat(68) + "╝";
    
    public static void main(String[] args) {
        printHeader();
        
        // Demonstrate each pattern
        demonstrateObserverPattern();
        System.out.println();
        
        demonstrateStrategyPattern();
        System.out.println();
        
        demonstrateChainOfResponsibility();
        System.out.println();
        
        demonstrateIntegratedSystem();
        
        printFooter();
    }
    
    private static void printHeader() {
        System.out.println(HEADER_LINE);
        System.out.println("║" + centerText("BEHAVIORAL DESIGN PATTERNS DEMONSTRATION", 68) + "║");
        System.out.println("║" + centerText("Lab 3 - TMPS Course", 68) + "║");
        System.out.println("║" + centerText("Computer Components Store", 68) + "║");
        System.out.println(FOOTER_LINE);
        System.out.println();
    }
    
    private static void printFooter() {
        System.out.println(HEADER_LINE);
        System.out.println("║" + centerText("Demonstration Complete", 68) + "║");
        System.out.println("║" + centerText("All patterns working correctly!", 68) + "║");
        System.out.println(FOOTER_LINE);
    }
    
    private static void demonstrateObserverPattern() {
        printSectionHeader("PATTERN 1: OBSERVER - Order Status Notifications");
        
        // Create order tracker (Subject)
        OrderTracker tracker = new OrderTracker();
        
        // Create observers
        OrderObserver emailNotifier = new EmailNotifier("customer@example.com");
        OrderObserver smsNotifier = new SMSNotifier("+1-555-0123");
        OrderObserver logger = new LoggingObserver();
        
        // Attach observers
        tracker.attach(emailNotifier);
        tracker.attach(smsNotifier);
        tracker.attach(logger);
        
        System.out.println();
        
        // Create and track an order
        Order order = new Order(1001, "customer@example.com");
        order.addComponent(new CPU("Intel Core i9-13900K", 589.99, 24, 3.0));
        order.addComponent(new GPU("NVIDIA RTX 4090", 1599.99, 24));
        
        System.out.println("Created order: " + order);
        System.out.println();
        
        // Update status multiple times - observers get notified
        System.out.println("Updating order status (observers will be notified):");
        tracker.updateOrderStatus(order, OrderStatus.VALIDATED, "Order validated successfully");
        System.out.println();
        
        tracker.updateOrderStatus(order, OrderStatus.PROCESSING, "Order is being processed");
        System.out.println();
        
        tracker.updateOrderStatus(order, OrderStatus.SHIPPED, "Order has been shipped");
        System.out.println();
        
        System.out.println(" Observer pattern demonstrated successfully!");
    }
    
    private static void demonstrateStrategyPattern() {
        printSectionHeader("PATTERN 2: STRATEGY - Shipping Cost Calculation");
        
        // Create an order
        Order order = new Order(2001, "premium@example.com");
        order.addComponent(new RAM("Corsair Vengeance DDR5", 189.99, 32, 6000));
        order.addComponent(new Storage("Samsung 990 Pro", 199.99, 2000, "NVMe SSD"));
        order.addComponent(new CPU("AMD Ryzen 9 7950X", 549.99, 16, 4.5));
        
        System.out.println("Order details:");
        System.out.println("  Items: " + order.getComponentCount());
        System.out.println("  Total: $" + String.format("%.2f", order.getTotalPrice()));
        System.out.println("  Weight: " + order.getTotalWeight() + " kg");
        System.out.println();
        
        // Try different shipping strategies
        ShippingCalculator calculator = new ShippingCalculator(new StandardShippingStrategy());
        
        System.out.println("Testing different shipping strategies:");
        System.out.println();
        
        // Standard shipping
        System.out.println("1. Standard Shipping:");
        System.out.println("   " + calculator.getStrategyInfo());
        double cost = calculator.calculateShipping(order);
        System.out.println("   Cost: $" + String.format("%.2f", cost));
        System.out.println();
        
        // Express shipping
        calculator.setStrategy(new ExpressShippingStrategy());
        System.out.println("2. Express Shipping:");
        System.out.println("   " + calculator.getStrategyInfo());
        cost = calculator.calculateShipping(order);
        System.out.println("   Cost: $" + String.format("%.2f", cost));
        System.out.println();
        
        // Overnight shipping
        calculator.setStrategy(new OvernightShippingStrategy());
        System.out.println("3. Overnight Shipping:");
        System.out.println("   " + calculator.getStrategyInfo());
        cost = calculator.calculateShipping(order);
        System.out.println("   Cost: $" + String.format("%.2f", cost));
        System.out.println();
        
        // Free shipping (won't qualify)
        calculator.setStrategy(new FreeShippingStrategy());
        System.out.println("4. Free Shipping:");
        System.out.println("   " + calculator.getStrategyInfo());
        cost = calculator.calculateShipping(order);
        System.out.println("   Cost: $" + String.format("%.2f", cost));
        System.out.println("   (Order doesn't meet $500 minimum for free shipping)");
        System.out.println();
        
        System.out.println(" Strategy pattern demonstrated successfully!");
    }
    
    private static void demonstrateChainOfResponsibility() {
        printSectionHeader("PATTERN 3: CHAIN OF RESPONSIBILITY - Order Validation");
        
        // Build validation chain
        OrderValidator validationChain = ValidationChainBuilder.buildStandardChain();
        
        System.out.println("Validation Chain:");
        System.out.println("  Empty Order Check  Email Check  Minimum Value Check  Inventory Check");
        System.out.println();
        
        // Test 1: Valid order
        System.out.println("Test 1: Valid Order");
        Order validOrder = new Order(3001, "valid@example.com");
        validOrder.addComponent(new GPU("AMD Radeon RX 7900 XTX", 999.99, 24));
        
        ValidationResult result = validationChain.validate(validOrder);
        System.out.println("Result: " + result);
        System.out.println();
        
        // Test 2: Empty order
        System.out.println("Test 2: Empty Order (should fail)");
        Order emptyOrder = new Order(3002, "test@example.com");
        
        result = validationChain.validate(emptyOrder);
        System.out.println("Result: " + result);
        System.out.println();
        
        // Test 3: Invalid email
        System.out.println("Test 3: Invalid Email (should fail)");
        Order invalidEmailOrder = new Order(3003, "not-an-email");
        invalidEmailOrder.addComponent(new RAM("Kingston Fury", 79.99, 16, 3200));
        
        result = validationChain.validate(invalidEmailOrder);
        System.out.println("Result: " + result);
        System.out.println();
        
        // Test 4: Below minimum value
        System.out.println("Test 4: Below Minimum Value (should fail)");
        Order cheapOrder = new Order(3004, "cheap@example.com");
        cheapOrder.addComponent(new Storage("USB Flash Drive", 15.99, 64, "USB"));
        
        result = validationChain.validate(cheapOrder);
        System.out.println("Result: " + result);
        System.out.println();
        
        System.out.println(" Chain of Responsibility pattern demonstrated successfully!");
    }
    
    private static void demonstrateIntegratedSystem() {
        printSectionHeader("INTEGRATED SYSTEM - All Patterns Working Together");
        
        System.out.println("Creating a complete order processing workflow...");
        System.out.println();
        
        // 1. Create order
        Order order = new Order(4001, "integrated@example.com");
        order.addComponent(new CPU("Intel Core i7-13700K", 409.99, 16, 3.4));
        order.addComponent(new GPU("NVIDIA RTX 4070 Ti", 799.99, 12));
        order.addComponent(new RAM("G.Skill Trident Z5", 159.99, 32, 6400));
        order.addComponent(new Storage("WD Black SN850X", 179.99, 2000, "NVMe SSD"));
        
        System.out.println("Step 1: Order Created");
        System.out.println("  " + order);
        for (Component component : order.getComponents()) {
            System.out.println("    - " + component);
        }
        System.out.println();
        
        // 2. Validate order (Chain of Responsibility)
        System.out.println("Step 2: Validating Order (Chain of Responsibility)");
        OrderValidator validator = ValidationChainBuilder.buildStandardChain();
        ValidationResult validationResult = validator.validate(order);
        
        if (!validationResult.isValid()) {
            System.out.println("  ✗ Validation failed: " + validationResult.getMessage());
            return;
        }
        System.out.println("  " + validationResult);
        System.out.println();
        
        // 3. Calculate shipping (Strategy)
        System.out.println("Step 3: Calculating Shipping (Strategy Pattern)");
        ShippingCalculator calculator = new ShippingCalculator(new ExpressShippingStrategy());
        double shippingCost = calculator.calculateShipping(order);
        System.out.println("  Method: " + calculator.getStrategyInfo());
        System.out.println("  Shipping Cost: $" + String.format("%.2f", shippingCost));
        System.out.println("  Final Total: $" + String.format("%.2f", order.getFinalPrice()));
        System.out.println();
        
        // 4. Track order status (Observer)
        System.out.println("Step 4: Processing Order (Observer Pattern)");
        OrderTracker tracker = new OrderTracker();
        tracker.attach(new EmailNotifier(order.getCustomerEmail()));
        tracker.attach(new LoggingObserver());
        
        tracker.updateOrderStatus(order, OrderStatus.VALIDATED, "Payment processed");
        System.out.println();
        tracker.updateOrderStatus(order, OrderStatus.PROCESSING, "Components being assembled");
        System.out.println();
        tracker.updateOrderStatus(order, OrderStatus.SHIPPED, "Package handed to carrier");
        System.out.println();
        
        System.out.println(" Complete order workflow executed successfully!");
        System.out.println("  All three behavioral patterns worked together seamlessly.");
    }
    
    private static void printSectionHeader(String title) {
        System.out.println(HEADER_LINE);
        System.out.println("║ " + title + " ".repeat(68 - title.length() - 2) + "║");
        System.out.println(FOOTER_LINE);
        System.out.println();
    }
    
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }
}
