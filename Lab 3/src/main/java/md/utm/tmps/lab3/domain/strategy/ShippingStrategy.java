package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Strategy interface for different shipping calculation methods
 * Part of the Strategy Pattern
 */
public interface ShippingStrategy {
    /**
     * Calculate shipping cost for an order
     * @param order The order to calculate shipping for
     * @return The shipping cost
     */
    double calculateShipping(Order order);
    
    /**
     * Get the name of this shipping strategy
     */
    String getStrategyName();
    
    /**
     * Get estimated delivery time in days
     */
    int getEstimatedDeliveryDays();
}
