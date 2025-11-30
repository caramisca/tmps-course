package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete strategy for express shipping
 * Higher cost but faster delivery
 * Part of the Strategy Pattern
 */
public class ExpressShippingStrategy implements ShippingStrategy {
    private static final double BASE_COST = 15.99;
    private static final double COST_PER_KG = 4.00;

    @Override
    public double calculateShipping(Order order) {
        double weight = order.getTotalWeight();
        return BASE_COST + (weight * COST_PER_KG);
    }

    @Override
    public String getStrategyName() {
        return "Express Shipping";
    }

    @Override
    public int getEstimatedDeliveryDays() {
        return 2;
    }
}
