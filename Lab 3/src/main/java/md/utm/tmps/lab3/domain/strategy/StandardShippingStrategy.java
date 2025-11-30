package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete strategy for standard shipping
 * Calculates shipping based on order weight
 * Part of the Strategy Pattern
 */
public class StandardShippingStrategy implements ShippingStrategy {
    private static final double BASE_COST = 5.99;
    private static final double COST_PER_KG = 2.50;

    @Override
    public double calculateShipping(Order order) {
        double weight = order.getTotalWeight();
        return BASE_COST + (weight * COST_PER_KG);
    }

    @Override
    public String getStrategyName() {
        return "Standard Shipping";
    }

    @Override
    public int getEstimatedDeliveryDays() {
        return 5;
    }
}
