package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete strategy for free shipping (for orders over threshold)
 * No cost but longer delivery time
 * Part of the Strategy Pattern
 */
public class FreeShippingStrategy implements ShippingStrategy {
    private static final double MINIMUM_ORDER_VALUE = 500.00;

    @Override
    public double calculateShipping(Order order) {
        if (order.getTotalPrice() >= MINIMUM_ORDER_VALUE) {
            return 0.0;
        }
        // If order doesn't meet threshold, fall back to standard shipping
        return new StandardShippingStrategy().calculateShipping(order);
    }

    @Override
    public String getStrategyName() {
        return "Free Shipping (orders over $" + MINIMUM_ORDER_VALUE + ")";
    }

    @Override
    public int getEstimatedDeliveryDays() {
        return 7;
    }
}
