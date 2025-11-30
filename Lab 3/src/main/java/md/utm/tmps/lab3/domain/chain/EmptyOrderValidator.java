package md.utm.tmps.lab3.domain.chain;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete validator that checks if order has items
 * Part of the Chain of Responsibility Pattern
 */
public class EmptyOrderValidator extends OrderValidator {
    
    @Override
    public ValidationResult validate(Order order) {
        System.out.println("   Checking if order has items...");
        
        if (order.getComponents().isEmpty()) {
            return ValidationResult.failure("Order is empty - no components added");
        }
        
        System.out.println("     Order has " + order.getComponentCount() + " items");
        return passToNext(order);
    }
}
