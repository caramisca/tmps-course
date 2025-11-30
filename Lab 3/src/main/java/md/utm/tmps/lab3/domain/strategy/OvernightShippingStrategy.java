package md.utm.tmps.lab3.domain.strategy;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete strategy for overnight shipping
 * Premium cost with next-day delivery
 * Part of the Strategy Pattern
 */
public class OvernightShippingStrategy implements ShippingStrategy {
    private static final double BASE_COST = 29.99;
    private static final double COST_PER_KG = 6.50;

    @Override
    public double calculateShipping(Order order) {
        double weight = order.getTotalWeight();
        return BASE_COST + (weight * COST_PER_KG);
    }

    @Override
    public String getStrategyName() {
        return "Overnight Shipping";
    }

    @Override
    public int getEstimatedDeliveryDays() {
        return 1;
    }
}
