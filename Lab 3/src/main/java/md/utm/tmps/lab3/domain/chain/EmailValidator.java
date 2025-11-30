package md.utm.tmps.lab3.domain.chain;

import md.utm.tmps.lab3.domain.models.Order;
import java.util.regex.Pattern;

/**
 * Concrete validator that checks customer email format
 * Part of the Chain of Responsibility Pattern
 */
public class EmailValidator extends OrderValidator {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    @Override
    public ValidationResult validate(Order order) {
        System.out.println("   Validating customer email...");
        
        String email = order.getCustomerEmail();
        if (email == null || email.isBlank()) {
            return ValidationResult.failure("Customer email is missing");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ValidationResult.failure("Customer email format is invalid: " + email);
        }
        
        System.out.println("     Email is valid");
        return passToNext(order);
    }
}
