package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Observer interface for receiving order status notifications
 * Part of the Observer Pattern
 */
public interface OrderObserver {
    /**
     * Called when an order status changes
     * @param order The order that changed
     * @param message Additional information about the change
     */
    void onOrderStatusChanged(Order order, String message);
}
