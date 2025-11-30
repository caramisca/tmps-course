package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Subject interface for managing observers
 * Part of the Observer Pattern
 */
public interface OrderSubject {
    /**
     * Attach an observer to receive notifications
     */
    void attach(OrderObserver observer);
    
    /**
     * Detach an observer from receiving notifications
     */
    void detach(OrderObserver observer);
    
    /**
     * Notify all attached observers of a change
     */
    void notifyObservers(Order order, String message);
}
