package md.utm.tmps.lab3.domain.chain;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Abstract handler for order validation chain
 * Part of the Chain of Responsibility Pattern
 */
public abstract class OrderValidator {
    protected OrderValidator nextValidator;

    /**
     * Set the next validator in the chain
     */
    public OrderValidator setNext(OrderValidator validator) {
        this.nextValidator = validator;
        return validator;
    }

    /**
     * Validate the order and pass to next validator if successful
     * @param order The order to validate
     * @return ValidationResult indicating success or failure
     */
    public abstract ValidationResult validate(Order order);

    /**
     * Helper method to pass validation to the next handler
     */
    protected ValidationResult passToNext(Order order) {
        if (nextValidator != null) {
            return nextValidator.validate(order);
        }
        return ValidationResult.success("All validations passed");
    }
}
