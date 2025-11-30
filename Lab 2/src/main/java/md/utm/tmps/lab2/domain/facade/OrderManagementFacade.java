package md.utm.tmps.lab2.domain.facade;

import md.utm.tmps.lab2.domain.models.Order;
import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.subsystems.*;

/**
 * FACADE PATTERN:
 * Provides a unified, simplified interface to a complex subsystem.
 * It defines a higher-level interface that makes the subsystem easier to use.
 * 
 * SINGLE RESPONSIBILITY: This facade has ONE responsibility - to provide
 * a simplified interface for the complete order workflow. It hides the
 * complexity of coordinating multiple subsystems behind simple, intuitive methods.
 * 
 * USE CASE: OrderManagementFacade simplifies the complex process by coordinating:
 * - OrderIdGenerator (ID generation)
 * - InventoryManager (order tracking)
 * - KitchenService (food preparation)
 * - NotificationService (customer communication)
 * 
 * Without the facade, clients would need to:
 * 1. Know about all subsystems and their APIs
 * 2. Understand the correct sequence of operations
 * 3. Handle coordination and error checking manually
 * 4. Manage state transitions across multiple objects
 */
public class OrderManagementFacade {
    // Subsystems - encapsulated and hidden from client
    private final OrderIdGenerator idGenerator;
    private final InventoryManager inventoryManager;
    private final KitchenService kitchenService;
    private final NotificationService notificationService;
    
    public OrderManagementFacade() {
        this.idGenerator = OrderIdGenerator.getInstance();
        this.inventoryManager = new InventoryManager();
        this.kitchenService = new KitchenService();
        this.notificationService = new NotificationService();
    }
    
    /**
     * HIGH-LEVEL OPERATION: Place a complete order from start to finish
     * 
     * This single method encapsulates the entire ordering workflow:
     * 1. Create order with unique ID
     * 2. Add pizza to order
     * 3. Send order to kitchen for preparation
     * 4. Notify customer
     * 5. Track status through completion
     * 
     * Client doesn't need to know about any subsystems!
     * 
     * @param pizza the pizza to order
     * @param customerContact customer's contact information
     * @return the order ID for payment
     */
    public int placeOrder(PizzaComponent pizza, String customerContact) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Placing New Order                                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Step 1: Create order (coordinates ID generation and inventory)
        int orderId = idGenerator.generateOrderId();
        Order order = new Order(orderId);
        inventoryManager.registerOrder(order);
        
        // Step 2: Add pizza to order
        order.addPizza(pizza);
        System.out.println(" Pizza added to order");
        
        // Step 3: Send to kitchen for preparation
        inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.PREPARING);
        notificationService.sendOrderConfirmation(orderId, customerContact);
        
        kitchenService.prepareOrder(orderId);
        kitchenService.preparePizza(pizza);
        
        // Step 4: Mark as ready
        inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.READY);
        kitchenService.completeOrder(orderId);
        notificationService.sendOrderReady(orderId, customerContact);
        
        System.out.println("\n Order #" + orderId + " is ready for payment\n");
        return orderId;
    }
    
    /**
     * HIGH-LEVEL OPERATION: Complete payment and finalize order
     * 
     * This single method encapsulates the entire payment workflow:
     * 1. Validate order exists and is ready
     * 2. Validate payment details
     * 3. Process payment through payment processor
     * 4. Update order status
     * 5. Send confirmation to customer
     * 
     * Client doesn't need to coordinate subsystems manually!
     * 
     * @param orderId the order to pay for
     * @param paymentProcessor the payment method to use
     * @param paymentInfo payment information (email, token, or cash)
     * @return true if payment successful, false otherwise
     */
    public boolean completePayment(int orderId, PaymentProcessor paymentProcessor, String paymentInfo) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Processing Payment for Order #" + String.format("%-5d", orderId) + "                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Step 1: Validate order
        Order order = inventoryManager.getOrder(orderId);
        if (order == null) {
            System.out.println("✗ Order not found");
            return false;
        }
        
        if (order.getStatus() != Order.OrderStatus.READY) {
            System.out.println("✗ Order is not ready for payment (Status: " + order.getStatus() + ")");
            return false;
        }
        
        // Step 2: Validate payment details
        if (!paymentProcessor.validatePaymentDetails(paymentInfo)) {
            System.out.println("✗ Invalid payment details");
            return false;
        }
        
        // Step 3: Process payment
        System.out.println("Processing payment via " + paymentProcessor.getPaymentMethod() + "...");
        boolean paymentSuccess = paymentProcessor.processPayment(order.getTotalAmount(), paymentInfo);
        
        // Step 4: Update status and notify
        if (paymentSuccess) {
            inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.PAID);
            inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.COMPLETED);
            notificationService.sendPaymentConfirmation(orderId, order.getTotalAmount(), 
                                                       paymentProcessor.getPaymentMethod());
            
            System.out.println("\n Payment successful! Order #" + orderId + " completed\n");
            return true;
        } else {
            System.out.println("\n✗ Payment failed\n");
            return false;
        }
    }
    
    /**
     * SIMPLIFIED QUERY: Get order summary
     * Provides a simple way to check order status without knowing about InventoryManager
     * 
     * @param orderId the order to retrieve
     * @return Order object or null if not found
     */
    public Order getOrderStatus(int orderId) {
        return inventoryManager.getOrder(orderId);
    }
}
