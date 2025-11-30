package md.utm.tmps.lab3;

import md.utm.tmps.lab3.domain.models.*;
import md.utm.tmps.lab3.domain.observer.*;
import md.utm.tmps.lab3.domain.strategy.*;
import md.utm.tmps.lab3.domain.chain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Comprehensive test suite for all Behavioral Design Patterns
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BehavioralPatternsTest {
    
    private md.utm.tmps.lab3.domain.models.Order testOrder;
    
    @BeforeEach
    void setUp() {
        testOrder = new Order(1000, "test@example.com");
        testOrder.addComponent(new CPU("Test CPU", 500.00, 8, 3.5));
        testOrder.addComponent(new GPU("Test GPU", 700.00, 12));
    }
    
    // ============================================================================
    // OBSERVER PATTERN TESTS
    // ============================================================================
    
    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("Observer: Attach and notify observers")
    void testObserverAttachAndNotify() {
        OrderTracker tracker = new OrderTracker();
        
        // Create a custom observer to count notifications
        final int[] notificationCount = {0};
        OrderObserver testObserver = (order, message) -> notificationCount[0]++;
        
        tracker.attach(testObserver);
        tracker.updateOrderStatus(testOrder, OrderStatus.PROCESSING, "Test message");
        
        assertEquals(1, notificationCount[0], "Observer should be notified once");
        assertEquals(OrderStatus.PROCESSING, testOrder.getStatus(), "Order status should be updated");
    }
    
    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("Observer: Multiple observers receive notifications")
    void testMultipleObservers() {
        OrderTracker tracker = new OrderTracker();
        
        final int[] emailCount = {0};
        final int[] smsCount = {0};
        
        OrderObserver emailObserver = (order, message) -> emailCount[0]++;
        OrderObserver smsObserver = (order, message) -> smsCount[0]++;
        
        tracker.attach(emailObserver);
        tracker.attach(smsObserver);
        
        tracker.updateOrderStatus(testOrder, OrderStatus.SHIPPED, "Shipped");
        
        assertEquals(1, emailCount[0], "Email observer should be notified");
        assertEquals(1, smsCount[0], "SMS observer should be notified");
    }
    
    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("Observer: Detach observer stops notifications")
    void testObserverDetach() {
        OrderTracker tracker = new OrderTracker();
        
        final int[] count = {0};
        OrderObserver observer = (order, message) -> count[0]++;
        
        tracker.attach(observer);
        tracker.updateOrderStatus(testOrder, OrderStatus.PROCESSING, "Test 1");
        assertEquals(1, count[0], "Should receive notification when attached");
        
        tracker.detach(observer);
        tracker.updateOrderStatus(testOrder, OrderStatus.SHIPPED, "Test 2");
        assertEquals(1, count[0], "Should not receive notification after detach");
    }
    
    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("Observer: EmailNotifier formats messages correctly")
    void testEmailNotifier() {
        EmailNotifier notifier = new EmailNotifier("test@example.com");
        assertEquals("test@example.com", notifier.getRecipientEmail());
        
        // Test that it doesn't throw exceptions
        assertDoesNotThrow(() -> 
            notifier.onOrderStatusChanged(testOrder, "Test message")
        );
    }
    
    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("Observer: SMSNotifier formats messages correctly")
    void testSMSNotifier() {
        SMSNotifier notifier = new SMSNotifier("+1-555-0123");
        assertEquals("+1-555-0123", notifier.getPhoneNumber());
        
        assertDoesNotThrow(() -> 
            notifier.onOrderStatusChanged(testOrder, "Test message")
        );
    }
    
    // ============================================================================
    // STRATEGY PATTERN TESTS
    // ============================================================================
    
    @Test
    @org.junit.jupiter.api.Order(6)
    @DisplayName("Strategy: StandardShippingStrategy calculates correctly")
    void testStandardShipping() {
        ShippingStrategy strategy = new StandardShippingStrategy();
        double cost = strategy.calculateShipping(testOrder);
        
        // Base $5.99 + (1.0 kg * $2.50) = $8.49
        assertEquals(8.49, cost, 0.01, "Standard shipping cost should be correct");
        assertEquals("Standard Shipping", strategy.getStrategyName());
        assertEquals(5, strategy.getEstimatedDeliveryDays());
    }
    
    @Test
    @org.junit.jupiter.api.Order(7)
    @DisplayName("Strategy: ExpressShippingStrategy calculates correctly")
    void testExpressShipping() {
        ShippingStrategy strategy = new ExpressShippingStrategy();
        double cost = strategy.calculateShipping(testOrder);
        
        // Base $15.99 + (1.0 kg * $4.00) = $19.99
        assertEquals(19.99, cost, 0.01, "Express shipping cost should be correct");
        assertEquals(2, strategy.getEstimatedDeliveryDays());
    }
    
    @Test
    @org.junit.jupiter.api.Order(8)
    @DisplayName("Strategy: OvernightShippingStrategy calculates correctly")
    void testOvernightShipping() {
        ShippingStrategy strategy = new OvernightShippingStrategy();
        double cost = strategy.calculateShipping(testOrder);
        
        // Base $29.99 + (1.0 kg * $6.50) = $36.49
        assertEquals(36.49, cost, 0.01, "Overnight shipping cost should be correct");
        assertEquals(1, strategy.getEstimatedDeliveryDays());
    }
    
    @Test
    @org.junit.jupiter.api.Order(9)
    @DisplayName("Strategy: FreeShippingStrategy requires minimum order")
    void testFreeShipping() {
        ShippingStrategy strategy = new FreeShippingStrategy();
        
        // Test with order below threshold ($50 total)
        Order smallOrder = new Order(1001, "small@example.com");
        smallOrder.addComponent(new Storage("USB Drive", 50.00, 64, "USB"));
        double cost1 = strategy.calculateShipping(smallOrder);
        assertTrue(cost1 > 0, "Should charge shipping for orders below $500");
        
        // Test with order above threshold ($1200 total)
        double cost2 = strategy.calculateShipping(testOrder);
        assertEquals(0.0, cost2, 0.01, "Should be free for orders over $500");
    }
    
    @Test
    @org.junit.jupiter.api.Order(10)
    @DisplayName("Strategy: ShippingCalculator changes strategies dynamically")
    void testShippingCalculatorStrategyChange() {
        ShippingCalculator calculator = new ShippingCalculator(new StandardShippingStrategy());
        
        double standardCost = calculator.calculateShipping(testOrder);
        assertTrue(standardCost > 0, "Standard shipping should have cost");
        
        calculator.setStrategy(new ExpressShippingStrategy());
        double expressCost = calculator.calculateShipping(testOrder);
        assertTrue(expressCost > standardCost, "Express should be more expensive than standard");
        
        calculator.setStrategy(new OvernightShippingStrategy());
        double overnightCost = calculator.calculateShipping(testOrder);
        assertTrue(overnightCost > expressCost, "Overnight should be most expensive");
    }
    
    @Test
    @org.junit.jupiter.api.Order(11)
    @DisplayName("Strategy: Calculator updates order shipping cost")
    void testShippingCalculatorUpdatesOrder() {
        ShippingCalculator calculator = new ShippingCalculator(new StandardShippingStrategy());
        
        assertEquals(0.0, testOrder.getShippingCost(), "Initial shipping cost should be 0");
        
        calculator.calculateShipping(testOrder);
        assertTrue(testOrder.getShippingCost() > 0, "Shipping cost should be updated");
        assertTrue(testOrder.getFinalPrice() > testOrder.getTotalPrice(), 
            "Final price should include shipping");
    }
    
    // ============================================================================
    // CHAIN OF RESPONSIBILITY PATTERN TESTS
    // ============================================================================
    
    @Test
    @org.junit.jupiter.api.Order(12)
    @DisplayName("Chain: EmptyOrderValidator rejects empty orders")
    void testEmptyOrderValidator() {
        OrderValidator validator = new EmptyOrderValidator();
        
        Order emptyOrder = new Order(3000, "test@example.com");
        ValidationResult result = validator.validate(emptyOrder);
        
        assertFalse(result.isValid(), "Empty order should fail validation");
        assertTrue(result.getMessage().contains("empty"), "Message should mention empty order");
    }
    
    @Test
    @org.junit.jupiter.api.Order(13)
    @DisplayName("Chain: EmailValidator validates email format")
    void testEmailValidator() {
        OrderValidator validator = new EmailValidator();
        
        // Invalid email
        Order invalidOrder = new Order(3001, "not-an-email");
        invalidOrder.addComponent(new RAM("Test RAM", 100.00, 16, 3200));
        ValidationResult result1 = validator.validate(invalidOrder);
        assertFalse(result1.isValid(), "Invalid email should fail");
        
        // Valid email
        Order validOrder = new Order(3002, "valid@example.com");
        validOrder.addComponent(new RAM("Test RAM", 100.00, 16, 3200));
        ValidationResult result2 = validator.validate(validOrder);
        assertTrue(result2.isValid(), "Valid email should pass");
    }
    
    @Test
    @org.junit.jupiter.api.Order(14)
    @DisplayName("Chain: MinimumOrderValidator checks order value")
    void testMinimumOrderValidator() {
        OrderValidator validator = new MinimumOrderValidator();
        
        // Below minimum
        Order cheapOrder = new Order(3003, "test@example.com");
        cheapOrder.addComponent(new Storage("Cheap Storage", 20.00, 128, "USB"));
        ValidationResult result1 = validator.validate(cheapOrder);
        assertFalse(result1.isValid(), "Order below minimum should fail");
        
        // Above minimum
        ValidationResult result2 = validator.validate(testOrder);
        assertTrue(result2.isValid(), "Order above minimum should pass");
    }
    
    @Test
    @org.junit.jupiter.api.Order(15)
    @DisplayName("Chain: InventoryValidator checks stock availability")
    void testInventoryValidator() {
        OrderValidator validator = new InventoryValidator();
        
        // Should pass for normal items
        ValidationResult result = validator.validate(testOrder);
        assertTrue(result.isValid(), "Normal order should pass inventory check");
    }
    
    @Test
    @org.junit.jupiter.api.Order(16)
    @DisplayName("Chain: ValidationChainBuilder creates proper chain")
    void testValidationChainBuilder() {
        OrderValidator chain = ValidationChainBuilder.buildStandardChain();
        assertNotNull(chain, "Chain should be created");
        
        // Valid order should pass all checks
        ValidationResult result = chain.validate(testOrder);
        assertTrue(result.isValid(), "Valid order should pass all validations");
    }
    
    @Test
    @org.junit.jupiter.api.Order(17)
    @DisplayName("Chain: Chain stops at first failure")
    void testChainStopsAtFirstFailure() {
        OrderValidator chain = ValidationChainBuilder.buildStandardChain();
        
        // Empty order should fail at first validator
        Order emptyOrder = new Order(3004, "test@example.com");
        ValidationResult result = chain.validate(emptyOrder);
        
        assertFalse(result.isValid(), "Empty order should fail");
        assertTrue(result.getMessage().toLowerCase().contains("empty"), 
            "Should fail at empty check");
    }
    
    @Test
    @org.junit.jupiter.api.Order(18)
    @DisplayName("Chain: Custom chain can be built")
    void testCustomChain() {
        OrderValidator customChain = ValidationChainBuilder.buildCustomChain(
            new EmailValidator(),
            new MinimumOrderValidator()
        );
        
        assertNotNull(customChain, "Custom chain should be created");
        
        // Should fail on minimum even with valid email
        Order cheapOrder = new Order(3005, "valid@example.com");
        cheapOrder.addComponent(new Storage("USB Drive", 15.00, 64, "USB"));
        
        ValidationResult result = customChain.validate(cheapOrder);
        assertFalse(result.isValid(), "Should fail on minimum value check");
    }
    
    // ============================================================================
    // INTEGRATION TESTS
    // ============================================================================
    
    @Test
    @org.junit.jupiter.api.Order(19)
    @DisplayName("Integration: Complete order workflow")
    void testCompleteOrderWorkflow() {
        // 1. Create order with components
        Order order = new Order(5000, "integration@example.com");
        order.addComponent(new CPU("Intel i9", 600.00, 16, 3.8));
        order.addComponent(new GPU("RTX 4080", 1200.00, 16));
        
        // 2. Validate order
        OrderValidator validator = ValidationChainBuilder.buildStandardChain();
        ValidationResult validationResult = validator.validate(order);
        assertTrue(validationResult.isValid(), "Order should pass validation");
        
        // 3. Calculate shipping
        ShippingCalculator calculator = new ShippingCalculator(new ExpressShippingStrategy());
        double shippingCost = calculator.calculateShipping(order);
        assertTrue(shippingCost > 0, "Shipping should be calculated");
        
        // 4. Track with observers
        OrderTracker tracker = new OrderTracker();
        final int[] notificationCount = {0};
        tracker.attach((o, m) -> notificationCount[0]++);
        
        tracker.updateOrderStatus(order, OrderStatus.PROCESSING, "Processing");
        tracker.updateOrderStatus(order, OrderStatus.SHIPPED, "Shipped");
        
        assertEquals(2, notificationCount[0], "Should receive 2 notifications");
        assertEquals(OrderStatus.SHIPPED, order.getStatus(), "Status should be updated");
    }
    
    @Test
    @org.junit.jupiter.api.Order(20)
    @DisplayName("Integration: Order with all patterns")
    void testOrderWithAllPatterns() {
        // Create order
        Order order = new Order(6000, "complete@example.com");
        order.addComponent(new CPU("AMD Ryzen 9", 550.00, 16, 4.2));
        order.addComponent(new RAM("Corsair DDR5", 200.00, 32, 6000));
        order.addComponent(new Storage("Samsung SSD", 180.00, 1000, "NVMe"));
        
        // Chain of Responsibility: Validate
        OrderValidator validator = ValidationChainBuilder.buildStandardChain();
        ValidationResult validation = validator.validate(order);
        assertTrue(validation.isValid(), "Order should be valid");
        
        // Strategy: Calculate shipping
        ShippingCalculator calc = new ShippingCalculator(new StandardShippingStrategy());
        calc.calculateShipping(order);
        assertTrue(order.getFinalPrice() > order.getTotalPrice(), 
            "Final price should include shipping");
        
        // Observer: Track status
        OrderTracker tracker = new OrderTracker();
        boolean[] notified = {false};
        tracker.attach((o, m) -> notified[0] = true);
        tracker.updateOrderStatus(order, OrderStatus.DELIVERED, "Delivered successfully");
        
        assertTrue(notified[0], "Observer should be notified");
        assertEquals(OrderStatus.DELIVERED, order.getStatus(), "Status should be DELIVERED");
    }
    
    // ============================================================================
    // MODEL TESTS
    // ============================================================================
    
    @Test
    @org.junit.jupiter.api.Order(21)
    @DisplayName("Models: Component creation and properties")
    void testComponentModels() {
        CPU cpu = new CPU("Test CPU", 500.00, 8, 3.5);
        assertEquals("Test CPU", cpu.getName());
        assertEquals(500.00, cpu.getPrice(), 0.01);
        assertEquals(8, cpu.getCores());
        assertEquals(3.5, cpu.getClockSpeed(), 0.01);
        
        GPU gpu = new GPU("Test GPU", 700.00, 12);
        assertEquals(12, gpu.getVram());
        
        RAM ram = new RAM("Test RAM", 150.00, 16, 3200);
        assertEquals(16, ram.getCapacity());
        assertEquals(3200, ram.getSpeed());
        
        Storage storage = new Storage("Test SSD", 100.00, 1000, "NVMe");
        assertEquals(1000, storage.getCapacity());
        assertEquals("NVMe", storage.getType());
    }
    
    @Test
    @org.junit.jupiter.api.Order(22)
    @DisplayName("Models: Order calculates totals correctly")
    void testOrderCalculations() {
        Order order = new Order(7000, "calc@example.com");
        assertEquals(0.0, order.getTotalPrice(), 0.01, "Initial price should be 0");
        
        order.addComponent(new CPU("CPU", 500.00, 8, 3.5));
        assertEquals(500.00, order.getTotalPrice(), 0.01);
        
        order.addComponent(new GPU("GPU", 700.00, 12));
        assertEquals(1200.00, order.getTotalPrice(), 0.01);
        
        order.setShippingCost(10.00);
        assertEquals(1210.00, order.getFinalPrice(), 0.01);
    }
}
