package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Context class that uses a ShippingStrategy
 * Part of the Strategy Pattern
 */
public class ShippingCalculator {
    private ShippingStrategy strategy;

    public ShippingCalculator(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Change the shipping strategy at runtime
     */
    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculate shipping using the current strategy
     */
    public double calculateShipping(Order order) {
        if (strategy == null) {
            throw new IllegalStateException("No shipping strategy set");
        }
        double cost = strategy.calculateShipping(order);
        order.setShippingCost(cost);
        return cost;
    }

    /**
     * Get information about the current strategy
     */
    public String getStrategyInfo() {
        if (strategy == null) {
            return "No strategy set";
        }
        return String.format("%s (Est. %d days)", 
            strategy.getStrategyName(), 
            strategy.getEstimatedDeliveryDays());
    }

    public ShippingStrategy getStrategy() {
        return strategy;
    }
}
