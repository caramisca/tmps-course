package md.utm.tmps.lab3.domain.chain;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete validator that checks minimum order value
 * Part of the Chain of Responsibility Pattern
 */
public class MinimumOrderValidator extends OrderValidator {
    private static final double MINIMUM_ORDER_VALUE = 50.00;
    
    @Override
    public ValidationResult validate(Order order) {
        System.out.println("   Checking minimum order value...");
        
        if (order.getTotalPrice() < MINIMUM_ORDER_VALUE) {
            return ValidationResult.failure(
                String.format("Order total ($%.2f) is below minimum ($%.2f)", 
                    order.getTotalPrice(), MINIMUM_ORDER_VALUE)
            );
        }
        
        System.out.println("     Order value is above minimum");
        return passToNext(order);
    }
}
