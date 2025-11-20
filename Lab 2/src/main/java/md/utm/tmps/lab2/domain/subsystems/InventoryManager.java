package md.utm.tmps.lab2.domain.subsystems;

import md.utm.tmps.lab2.domain.models.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Subsystem for managing order inventory and tracking
 */
public class InventoryManager {
    private final Map<Integer, Order> orders;
    
    public InventoryManager() {
        this.orders = new HashMap<>();
    }
    
    public void registerOrder(Order order) {
        orders.put(order.getOrderId(), order);
        System.out.println("  → Inventory: Order #" + order.getOrderId() + " registered");
    }
    
    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }
    
    public boolean orderExists(int orderId) {
        return orders.containsKey(orderId);
    }
    
    public void updateOrderStatus(int orderId, Order.OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            System.out.println("  → Inventory: Order #" + orderId + " status updated to " + status);
        }
    }
    
    public int getTotalOrders() {
        return orders.size();
    }
}
