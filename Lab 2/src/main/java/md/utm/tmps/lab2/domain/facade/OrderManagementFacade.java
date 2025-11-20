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
 * USE CASE: OrderManagementFacade simplifies the complex process of creating,
 * processing, and completing orders by coordinating multiple subsystems:
 * - OrderIdGenerator (ID generation)
 * - InventoryManager (order tracking)
 * - KitchenService (food preparation)
 * - NotificationService (customer communication)
 * - PaymentProcessor (payment handling)
 * 
 * Without the facade, clients would need to interact with all these subsystems
 * directly, understand their interdependencies, and manage the correct sequence
 * of operations.
 */
public class OrderManagementFacade {
    // Subsystems
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
     * Simplified method to create a new order
     * Internally coordinates ID generation and inventory registration
     * 
     * @return newly created Order object
     */
    public Order createOrder() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Creating New Order                                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        int orderId = idGenerator.generateOrderId();
        Order order = new Order(orderId);
        inventoryManager.registerOrder(order);
        
        System.out.println("✓ Order #" + orderId + " created successfully\n");
        return order;
    }
    
    /**
     * Simplified method to add a pizza to an order
     * Internally manages order lookup and total calculation
     * 
     * @param orderId the order to add to
     * @param pizza the pizza to add
     */
    public void addPizzaToOrder(int orderId, PizzaComponent pizza) {
        Order order = inventoryManager.getOrder(orderId);
        if (order != null) {
            order.addPizza(pizza);
            System.out.println("✓ Pizza added to Order #" + orderId);
        } else {
            System.out.println("✗ Order #" + orderId + " not found");
        }
    }
    
    /**
     * Simplified method to process an order through the kitchen
     * Coordinates kitchen service and inventory status updates
     * 
     * @param orderId the order to process
     * @param customerContact customer's contact for notifications
     */
    public void processOrder(int orderId, String customerContact) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Processing Order #" + String.format("%-5d", orderId) + "                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        Order order = inventoryManager.getOrder(orderId);
        if (order == null) {
            System.out.println("✗ Order not found");
            return;
        }
        
        // Update status to preparing
        inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.PREPARING);
        
        // Send confirmation
        notificationService.sendOrderConfirmation(orderId, customerContact);
        
        // Prepare in kitchen
        kitchenService.prepareOrder(orderId);
        for (PizzaComponent pizza : order.getPizzas()) {
            kitchenService.preparePizza(pizza);
        }
        
        // Update status to ready
        inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.READY);
        kitchenService.completeOrder(orderId);
        
        // Notify customer
        notificationService.sendOrderReady(orderId, customerContact);
        
        System.out.println("\n✓ Order #" + orderId + " is ready for payment and pickup\n");
    }
    
    /**
     * Simplified method to complete payment for an order
     * Coordinates payment processing, status updates, and notifications
     * 
     * @param orderId the order to pay for
     * @param paymentProcessor the payment method to use
     * @param paymentInfo payment information (email, token, or cash amount)
     * @param customerContact customer's contact for receipt
     * @return true if payment successful, false otherwise
     */
    public boolean completePayment(int orderId, PaymentProcessor paymentProcessor, 
                                   String paymentInfo, String customerContact) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Processing Payment for Order #" + String.format("%-5d", orderId) + "                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        Order order = inventoryManager.getOrder(orderId);
        if (order == null) {
            System.out.println("✗ Order not found");
            return false;
        }
        
        if (order.getStatus() != Order.OrderStatus.READY) {
            System.out.println("✗ Order is not ready for payment (Status: " + order.getStatus() + ")");
            return false;
        }
        
        // Validate payment details
        if (!paymentProcessor.validatePaymentDetails(paymentInfo)) {
            System.out.println("✗ Invalid payment details");
            return false;
        }
        
        // Process payment
        System.out.println("Processing payment via " + paymentProcessor.getPaymentMethod() + "...");
        boolean paymentSuccess = paymentProcessor.processPayment(order.getTotalAmount(), paymentInfo);
        
        if (paymentSuccess) {
            inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.PAID);
            inventoryManager.updateOrderStatus(orderId, Order.OrderStatus.COMPLETED);
            
            notificationService.sendPaymentConfirmation(orderId, order.getTotalAmount(), 
                                                       paymentProcessor.getPaymentMethod());
            
            System.out.println("\n✓ Payment successful! Order #" + orderId + " completed\n");
            return true;
        } else {
            System.out.println("\n✗ Payment failed\n");
            return false;
        }
    }
    
    /**
     * Simplified method to get order details
     * 
     * @param orderId the order to retrieve
     * @return Order object or null if not found
     */
    public Order getOrder(int orderId) {
        return inventoryManager.getOrder(orderId);
    }
    
    /**
     * Display order summary
     * 
     * @param orderId the order to display
     */
    public void displayOrder(int orderId) {
        Order order = inventoryManager.getOrder(orderId);
        if (order != null) {
            System.out.println(order);
        } else {
            System.out.println("Order #" + orderId + " not found");
        }
    }
    
    /**
     * Get statistics
     * 
     * @return total number of orders
     */
    public int getTotalOrdersProcessed() {
        return inventoryManager.getTotalOrders();
    }
}
