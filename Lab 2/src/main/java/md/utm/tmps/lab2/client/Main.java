package md.utm.tmps.lab2.client;

import md.utm.tmps.lab2.domain.decorators.*;
import md.utm.tmps.lab2.domain.facade.OrderManagementFacade;
import md.utm.tmps.lab2.domain.models.BasePizza;
import md.utm.tmps.lab2.domain.models.Order;
import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.payment.adapters.*;

/**
 * Main demonstration class for Structural Design Patterns
 * 
 * This class demonstrates:
 * 1. Decorator Pattern - Dynamic pizza topping addition with cost calculation
 * 2. Adapter Pattern - Integrating multiple payment systems with different interfaces
 * 3. Facade Pattern - Simplified order management coordinating multiple subsystems
 * 
 * Domain: Restaurant Food Ordering System
 * Author: Drumea Vasile
 */
public class Main {
    
    public static void main(String[] args) {
        printHeader();
        
        // Demonstrate Decorator Pattern
        demonstrateDecoratorPattern();
        
        // Demonstrate Adapter Pattern
        demonstrateAdapterPattern();
        
        // Demonstrate Facade Pattern
        demonstrateFacadePattern();
        
        // Complete end-to-end scenario using all patterns
        demonstrateCompleteScenario();
        
        printFooter();
    }
    
    private static void printHeader() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║     STRUCTURAL DESIGN PATTERNS DEMONSTRATION               ║");
        System.out.println("║     Lab 2 - TMPS Course                                    ║");
        System.out.println("║     Domain: Restaurant Food Ordering System                ║");
        System.out.println("║     Author: Drumea Vasile                                  ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    private static void demonstrateDecoratorPattern() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ PATTERN 1: DECORATOR - Dynamic Pizza Customization        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nPURPOSE: Add responsibilities to objects dynamically");
        System.out.println("USE CASE: Build custom pizzas by wrapping with topping decorators\n");
        
        // Example 1: Simple Margherita
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Example 1: Simple Margherita Pizza");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PizzaComponent margherita = new BasePizza("Medium", "Thin");
        margherita = new CheeseDecorator(margherita, "Mozzarella");
        
        System.out.println("\nDescription: " + margherita.getDescription());
        System.out.println("Cost: $" + String.format("%.2f", margherita.getCost()));
        System.out.println("\nPreparation steps:");
        margherita.prepare();
        
        // Example 2: Meat Lovers
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Example 2: Meat Lovers Pizza (Multiple Decorators)");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PizzaComponent meatLovers = new BasePizza("Large", "Thick");
        meatLovers = new CheeseDecorator(meatLovers, "Cheddar");
        meatLovers = new PepperoniDecorator(meatLovers);
        meatLovers = new BaconDecorator(meatLovers);
        
        System.out.println("\nDescription: " + meatLovers.getDescription());
        System.out.println("Cost: $" + String.format("%.2f", meatLovers.getCost()));
        System.out.println("\nPreparation steps:");
        meatLovers.prepare();
        
        // Example 3: Vegetarian Supreme
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Example 3: Vegetarian Supreme (Complex Decorators)");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PizzaComponent vegSupreme = new BasePizza("Extra Large", "Regular");
        vegSupreme = new CheeseDecorator(vegSupreme, "Parmesan");
        vegSupreme = new MushroomDecorator(vegSupreme);
        vegSupreme = new OliveDecorator(vegSupreme, "Black");
        vegSupreme = new VegetableDecorator(vegSupreme, "Bell Peppers");
        vegSupreme = new VegetableDecorator(vegSupreme, "Onions");
        vegSupreme = new VegetableDecorator(vegSupreme, "Tomatoes");
        
        System.out.println("\nDescription: " + vegSupreme.getDescription());
        System.out.println("Cost: $" + String.format("%.2f", vegSupreme.getCost()));
        System.out.println("\nPreparation steps:");
        vegSupreme.prepare();
        
        System.out.println("\n  DECORATOR PATTERN BENEFITS:");
        System.out.println("   Add toppings dynamically without modifying base class");
        System.out.println("  - Flexible combinations - any number of decorators");
        System.out.println("  - Each decorator adds its own cost and behavior");
        System.out.println("  - Open/Closed Principle: Open for extension, closed for modification");
    }
    
    private static void demonstrateAdapterPattern() {
        System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ PATTERN 2: ADAPTER - Payment System Integration           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nPURPOSE: Make incompatible interfaces work together");
        System.out.println("USE CASE: Integrate different payment systems (PayPal, Stripe, Cash)\n");
        
        double testAmount = 45.99;
        
        // Example 1: PayPal Adapter
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Example 1: PayPal Payment Adapter");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PaymentProcessor paypalProcessor = new PayPalAdapter();
        System.out.println("\nPayment Method: " + paypalProcessor.getPaymentMethod());
        System.out.println("Validating email: customer@example.com");
        
        if (paypalProcessor.validatePaymentDetails("customer@example.com")) {
            System.out.println(" Validation successful\n");
            paypalProcessor.processPayment(testAmount, "customer@example.com");
            System.out.println(" Payment completed successfully");
        }
        
        // Example 2: Stripe Adapter
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Example 2: Stripe Payment Adapter");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PaymentProcessor stripeProcessor = new StripeAdapter();
        System.out.println("\nPayment Method: " + stripeProcessor.getPaymentMethod());
        System.out.println("Validating card token: tok_visa_4242424242424242");
        
        if (stripeProcessor.validatePaymentDetails("tok_visa_4242424242424242")) {
            System.out.println(" Validation successful\n");
            stripeProcessor.processPayment(testAmount, "tok_visa_4242424242424242");
            System.out.println(" Payment completed successfully");
        }
        
        // Example 3: Cash Adapter
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Example 3: Cash Payment Adapter");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        PaymentProcessor cashProcessor = new CashAdapter();
        System.out.println("\nPayment Method: " + cashProcessor.getPaymentMethod());
        System.out.println("Validating cash amount: $50.00");
        
        if (cashProcessor.validatePaymentDetails("50.00")) {
            System.out.println(" Validation successful\n");
            cashProcessor.processPayment(testAmount, "50.00");
            System.out.println(" Payment completed successfully");
        }
        
        System.out.println("\n ADAPTER PATTERN BENEFITS:");
        System.out.println("  - Integrate third-party systems without modifying their code");
        System.out.println("  - Uniform interface for different payment methods");
        System.out.println("  - Easy to add new payment systems (just create new adapter)");
        System.out.println("  - Decouples client code from specific payment implementations");
    }
    
    private static void demonstrateFacadePattern() {
        System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ PATTERN 3: FACADE - Simplified Order Management           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nPURPOSE: Provide simplified interface to complex subsystem");
        System.out.println("USE CASE: Manage orders by coordinating multiple services\n");
        
        System.out.println("SUBSYSTEMS BEING COORDINATED:");
        System.out.println("  1. OrderIdGenerator - Generates unique order IDs");
        System.out.println("  2. InventoryManager - Tracks and manages orders");
        System.out.println("  3. KitchenService - Handles food preparation");
        System.out.println("  4. NotificationService - Sends customer notifications");
        System.out.println("  5. PaymentProcessor - Processes payments\n");
        
        System.out.println("WITHOUT FACADE - Client would need to:");
        System.out.println("  ✗ Know about all 5 subsystems");
        System.out.println("  ✗ Understand their dependencies");
        System.out.println("  ✗ Coordinate sequence of operations");
        System.out.println("  ✗ Handle error checking manually\n");
        
        System.out.println("WITH FACADE - Client just calls:");
        System.out.println("  ✓ placeOrder(pizza, contact)");
        System.out.println("  ✓ completePayment(orderId, payment, info)\n");
        
        // Create facade
        OrderManagementFacade orderFacade = new OrderManagementFacade();
        
        // Create and process a simple order
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Placing order with SIMPLIFIED INTERFACE");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        // Build pizza using Decorator pattern
        PizzaComponent pizza = new BasePizza("Medium", "Regular");
        pizza = new CheeseDecorator(pizza, "Mozzarella");
        pizza = new PepperoniDecorator(pizza);
        
        System.out.println("\nPizza: " + pizza.getDescription());
        System.out.println("Cost: $" + String.format("%.2f", pizza.getCost()));
        
        // ONE METHOD CALL handles entire order workflow!
        int orderId = orderFacade.placeOrder(pizza, "customer@email.com");
        
        // ONE METHOD CALL handles entire payment workflow!
        PaymentProcessor paymentMethod = new PayPalAdapter();
        boolean success = orderFacade.completePayment(orderId, paymentMethod, "customer@email.com");
        
        if (success) {
            Order order = orderFacade.getOrderStatus(orderId);
            System.out.println("\nFinal Order Status: " + order.getStatus());
            System.out.println("Total Paid: $" + String.format("%.2f", order.getTotalAmount()));
        }
        
        System.out.println("\n✓ FACADE PATTERN BENEFITS:");
        System.out.println("  - TWO simple method calls instead of 15+ subsystem calls");
        System.out.println("  - Client doesn't need to know about subsystems");
        System.out.println("  - Hides complexity of coordination and sequencing");
        System.out.println("  - Single Responsibility: Facade provides simplified workflow");
        System.out.println("  - Loose coupling between client and subsystems");
    }
    
    private static void demonstrateCompleteScenario() {
        System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ COMPLETE SCENARIO - All Patterns Working Together         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        OrderManagementFacade facade = new OrderManagementFacade();
        
        // Scenario: Customer orders a custom pizza and pays with Stripe
        
        System.out.println("Scenario: Customer places custom pizza order\n");
        
        // Build a complex pizza using Decorator pattern
        System.out.println("Building Supreme Pizza with multiple toppings:");
        PizzaComponent supremePizza = new BasePizza("Large", "Thick");
        supremePizza = new CheeseDecorator(supremePizza, "Mozzarella");
        supremePizza = new PepperoniDecorator(supremePizza);
        supremePizza = new MushroomDecorator(supremePizza);
        supremePizza = new OliveDecorator(supremePizza, "Black");
        supremePizza = new BaconDecorator(supremePizza);
        
        System.out.println("  " + supremePizza.getDescription());
        System.out.println("  Cost: $" + String.format("%.2f", supremePizza.getCost()));
        
        // Use Facade to place order (ONE simple call!)
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Step 1: Place Order (Facade coordinates all subsystems)");
        System.out.println("─────────────────────────────────────────────────────────────");
        int orderId = facade.placeOrder(supremePizza, "john.doe@email.com");
        
        // Check order status
        Order order = facade.getOrderStatus(orderId);
        System.out.println("\nOrder Status: " + order.getStatus());
        System.out.println("Order Total: $" + String.format("%.2f", order.getTotalAmount()));
        
        // Use Adapter for payment processing through Facade (ONE simple call!)
        System.out.println("\n─────────────────────────────────────────────────────────────");
        System.out.println("Step 2: Complete Payment (Adapter provides unified interface)");
        System.out.println("─────────────────────────────────────────────────────────────");
        PaymentProcessor stripePayment = new StripeAdapter();
        boolean success = facade.completePayment(orderId, stripePayment, 
                                                "tok_mastercard_5555555555554444");
        
        // Final status
        if (success) {
            order = facade.getOrderStatus(orderId);
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║ Order Successfully Completed                               ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("  Order ID: " + orderId);
            System.out.println("  Status: " + order.getStatus());
            System.out.println("  Total: $" + String.format("%.2f", order.getTotalAmount()));
            System.out.println("  Payment: " + stripePayment.getPaymentMethod());
        }
        
        System.out.println("\n✓ COMPLETE INTEGRATION SUCCESS:");
        System.out.println("  - DECORATOR: Built custom pizza with 5 dynamic toppings");
        System.out.println("  - FACADE: Placed order with ONE method call (hides 10+ subsystem calls)");
        System.out.println("  - ADAPTER: Processed payment through Stripe with unified interface");
        System.out.println("  - All patterns work seamlessly together!");
        System.out.println("  - Client code remains simple and clean!");
    }
    
    private static void printFooter() {
        System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║  DEMONSTRATION COMPLETE                                    ║");
        System.out.println("║                                                            ║");
        System.out.println("║  Structural Patterns Demonstrated:                        ║");
        System.out.println("║     Decorator - Dynamic object enhancement               ║");
        System.out.println("║     Adapter - Interface compatibility                    ║");
        System.out.println("║     Facade - Simplified subsystem access                 ║");
        System.out.println("║                                                            ║");
        System.out.println("║  Key Benefits:                                            ║");
        System.out.println("║    - Flexible object composition                          ║");
        System.out.println("║    - Easy third-party integration                         ║");
        System.out.println("║    - Reduced system complexity                            ║");
        System.out.println("║    - Improved maintainability                             ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}
