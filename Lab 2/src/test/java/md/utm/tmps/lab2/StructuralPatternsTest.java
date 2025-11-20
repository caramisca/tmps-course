package md.utm.tmps.lab2;

import md.utm.tmps.lab2.domain.decorators.*;
import md.utm.tmps.lab2.domain.facade.OrderManagementFacade;
import md.utm.tmps.lab2.domain.models.*;
import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.payment.adapters.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for Structural Design Patterns
 */
@DisplayName("Structural Design Patterns Tests")
public class StructuralPatternsTest {
    
    @Test
    @DisplayName("Decorator Pattern - Base Pizza")
    public void testBasePizza() {
        PizzaComponent pizza = new BasePizza("Medium", "Thin");
        
        assertEquals("Medium Thin crust pizza", pizza.getDescription());
        assertEquals(10.99, pizza.getCost(), 0.01);
    }
    
    @Test
    @DisplayName("Decorator Pattern - Single Topping")
    public void testSingleDecorator() {
        PizzaComponent pizza = new BasePizza("Large", "Thick");
        pizza = new CheeseDecorator(pizza, "Mozzarella");
        
        assertTrue(pizza.getDescription().contains("Mozzarella cheese"));
        assertEquals(12.99 + 1.50, pizza.getCost(), 0.01);
    }
    
    @Test
    @DisplayName("Decorator Pattern - Multiple Toppings")
    public void testMultipleDecorators() {
        PizzaComponent pizza = new BasePizza("Large", "Regular");
        pizza = new CheeseDecorator(pizza, "Cheddar");
        pizza = new PepperoniDecorator(pizza);
        pizza = new BaconDecorator(pizza);
        pizza = new MushroomDecorator(pizza);
        
        String desc = pizza.getDescription();
        assertTrue(desc.contains("Cheddar cheese"));
        assertTrue(desc.contains("pepperoni"));
        assertTrue(desc.contains("bacon"));
        assertTrue(desc.contains("mushrooms"));
        
        // Base: 12.99, Cheese: 1.50, Pepperoni: 2.00, Bacon: 2.50, Mushroom: 1.25
        assertEquals(12.99 + 1.50 + 2.00 + 2.50 + 1.25, pizza.getCost(), 0.01);
    }
    
    @Test
    @DisplayName("Decorator Pattern - Vegetarian Pizza")
    public void testVegetarianPizza() {
        PizzaComponent pizza = new BasePizza("Medium", "Thin");
        pizza = new CheeseDecorator(pizza, "Parmesan");
        pizza = new MushroomDecorator(pizza);
        pizza = new OliveDecorator(pizza, "Black");
        pizza = new VegetableDecorator(pizza, "Tomatoes");
        
        double expectedCost = 10.99 + 1.50 + 1.25 + 1.00 + 0.75;
        assertEquals(expectedCost, pizza.getCost(), 0.01);
    }
    
    @Test
    @DisplayName("Adapter Pattern - PayPal Validation")
    public void testPayPalAdapter() {
        PaymentProcessor paypal = new PayPalAdapter();
        
        assertEquals("PayPal", paypal.getPaymentMethod());
        assertTrue(paypal.validatePaymentDetails("customer@example.com"));
        assertFalse(paypal.validatePaymentDetails("invalid-email"));
        assertTrue(paypal.processPayment(25.50, "test@email.com"));
    }
    
    @Test
    @DisplayName("Adapter Pattern - Stripe Validation")
    public void testStripeAdapter() {
        PaymentProcessor stripe = new StripeAdapter();
        
        assertEquals("Stripe", stripe.getPaymentMethod());
        assertTrue(stripe.validatePaymentDetails("tok_visa_4242424242424242"));
        assertFalse(stripe.validatePaymentDetails("invalid_token"));
        assertTrue(stripe.processPayment(35.75, "tok_visa_4242424242424242"));
    }
    
    @Test
    @DisplayName("Adapter Pattern - Cash Validation")
    public void testCashAdapter() {
        PaymentProcessor cash = new CashAdapter();
        
        assertEquals("Cash", cash.getPaymentMethod());
        assertTrue(cash.validatePaymentDetails("50.00"));
        assertFalse(cash.validatePaymentDetails("invalid"));
        assertTrue(cash.processPayment(45.00, "50.00"));
    }
    
    @Test
    @DisplayName("Facade Pattern - Order Creation")
    public void testFacadeOrderCreation() {
        OrderManagementFacade facade = new OrderManagementFacade();
        Order order = facade.createOrder();
        
        assertNotNull(order);
        assertTrue(order.getOrderId() >= 1000);
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
    }
    
    @Test
    @DisplayName("Facade Pattern - Add Pizza to Order")
    public void testFacadeAddPizza() {
        OrderManagementFacade facade = new OrderManagementFacade();
        Order order = facade.createOrder();
        
        PizzaComponent pizza = new BasePizza("Large", "Thin");
        pizza = new CheeseDecorator(pizza, "Mozzarella");
        
        facade.addPizzaToOrder(order.getOrderId(), pizza);
        
        Order retrievedOrder = facade.getOrder(order.getOrderId());
        assertEquals(1, retrievedOrder.getPizzas().size());
        assertEquals(14.49, retrievedOrder.getTotalAmount(), 0.01);
    }
    
    @Test
    @DisplayName("Facade Pattern - Complete Order Workflow")
    public void testFacadeCompleteWorkflow() {
        OrderManagementFacade facade = new OrderManagementFacade();
        
        // Create order
        Order order = facade.createOrder();
        int orderId = order.getOrderId();
        
        // Add pizza
        PizzaComponent pizza = new BasePizza("Medium", "Regular");
        pizza = new CheeseDecorator(pizza, "Mozzarella");
        pizza = new PepperoniDecorator(pizza);
        facade.addPizzaToOrder(orderId, pizza);
        
        // Process order
        facade.processOrder(orderId, "test@email.com");
        Order processedOrder = facade.getOrder(orderId);
        assertEquals(Order.OrderStatus.READY, processedOrder.getStatus());
        
        // Complete payment
        PaymentProcessor payment = new PayPalAdapter();
        boolean paymentSuccess = facade.completePayment(orderId, payment, 
                                                       "test@email.com", "test@email.com");
        
        assertTrue(paymentSuccess);
        Order completedOrder = facade.getOrder(orderId);
        assertEquals(Order.OrderStatus.COMPLETED, completedOrder.getStatus());
    }
    
    @Test
    @DisplayName("Integration - All Patterns Together")
    public void testAllPatternsTogether() {
        OrderManagementFacade facade = new OrderManagementFacade();
        
        // Create order (Facade + Singleton)
        Order order = facade.createOrder();
        
        // Build custom pizza (Decorator)
        PizzaComponent pizza = new BasePizza("Large", "Thick");
        pizza = new CheeseDecorator(pizza, "Cheddar");
        pizza = new PepperoniDecorator(pizza);
        pizza = new BaconDecorator(pizza);
        
        // Add to order (Facade)
        facade.addPizzaToOrder(order.getOrderId(), pizza);
        
        // Process order (Facade)
        facade.processOrder(order.getOrderId(), "customer@test.com");
        
        // Pay with Stripe (Adapter + Facade)
        PaymentProcessor stripePayment = new StripeAdapter();
        boolean success = facade.completePayment(order.getOrderId(), stripePayment,
                                                "tok_visa_4242424242424242", "customer@test.com");
        
        assertTrue(success);
        
        Order finalOrder = facade.getOrder(order.getOrderId());
        assertEquals(Order.OrderStatus.COMPLETED, finalOrder.getStatus());
        
        // Verify cost calculation
        double expectedCost = 12.99 + 1.50 + 2.00 + 2.50; // Base + Cheese + Pepperoni + Bacon
        assertEquals(expectedCost, finalOrder.getTotalAmount(), 0.01);
    }
    
    @Test
    @DisplayName("Decorator Pattern - Cost Accumulation")
    public void testDecoratorCostAccumulation() {
        PizzaComponent pizza = new BasePizza("Small", "Thin");
        double baseCost = pizza.getCost();
        
        pizza = new CheeseDecorator(pizza, "Mozzarella");
        double withCheese = pizza.getCost();
        assertTrue(withCheese > baseCost);
        
        pizza = new PepperoniDecorator(pizza);
        double withPepperoni = pizza.getCost();
        assertTrue(withPepperoni > withCheese);
        
        pizza = new MushroomDecorator(pizza);
        double withMushrooms = pizza.getCost();
        assertTrue(withMushrooms > withPepperoni);
    }
    
    @Test
    @DisplayName("Adapter Pattern - All Payment Methods")
    public void testAllPaymentMethods() {
        double amount = 29.99;
        
        PaymentProcessor paypal = new PayPalAdapter();
        assertTrue(paypal.processPayment(amount, "user@example.com"));
        
        PaymentProcessor stripe = new StripeAdapter();
        assertTrue(stripe.processPayment(amount, "tok_visa_4242424242424242"));
        
        PaymentProcessor cash = new CashAdapter();
        assertTrue(cash.processPayment(amount, "30.00"));
    }
}
