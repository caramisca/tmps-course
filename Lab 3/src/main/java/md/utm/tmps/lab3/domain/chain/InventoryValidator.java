package md.utm.tmps.lab3.domain.chain;

import md.utm.tmps.lab3.domain.models.Order;
import md.utm.tmps.lab3.domain.models.Component;

/**
 * Concrete validator that checks inventory availability
 * Part of the Chain of Responsibility Pattern
 */
public class InventoryValidator extends OrderValidator {
    
    @Override
    public ValidationResult validate(Order order) {
        System.out.println("   Checking inventory availability...");
        
        // Simulate checking if components are in stock
        for (Component component : order.getComponents()) {
            // In a real system, we'd check actual inventory here
            if (component.getPrice() > 2000.00) {
                // Simulate out of stock for very expensive items occasionally
                if (Math.random() > 0.9) {
                    return ValidationResult.failure(
                        "Component out of stock: " + component.getName()
                    );
                }
            }
        }
        
        System.out.println("     All components are in stock");
        return passToNext(order);
    }
}
