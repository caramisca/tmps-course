package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;
import md.utm.tmps.lab3.domain.models.OrderStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of OrderSubject
 * Manages order status and notifies observers when status changes
 * Part of the Observer Pattern
 */
public class OrderTracker implements OrderSubject {
    private final List<OrderObserver> observers;

    public OrderTracker() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void attach(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("   Observer attached to order tracker");
        }
    }

    @Override
    public void detach(OrderObserver observer) {
        observers.remove(observer);
        System.out.println("   Observer detached from order tracker");
    }

    @Override
    public void notifyObservers(Order order, String message) {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(order, message);
        }
    }

    /**
     * Update order status and notify observers
     */
    public void updateOrderStatus(Order order, OrderStatus newStatus, String message) {
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);
        
        String notificationMessage = String.format(
            "Order #%d status changed: %s  %s. %s",
            order.getOrderId(), oldStatus, newStatus, message
        );
        
        notifyObservers(order, notificationMessage);
    }
}
