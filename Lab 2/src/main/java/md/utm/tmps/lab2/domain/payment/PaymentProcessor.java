package md.utm.tmps.lab2.domain.payment;

/**
 * Target interface - the interface our system expects
 * 
 * ADAPTER PATTERN:
 * Allows incompatible interfaces to work together. It acts as a bridge between
 * two incompatible interfaces by wrapping an object and exposing a different interface.
 * 
 * USE CASE: Integrate different payment systems (PayPal, Stripe, Cash) into our
 * restaurant ordering system with a unified interface
 */
public interface PaymentProcessor {
    /**
     * Process a payment
     * @param amount the amount to charge
     * @param accountInfo account/card information
     * @return true if payment successful, false otherwise
     */
    boolean processPayment(double amount, String accountInfo);
    
    /**
     * Get the payment method name
     * @return payment method name
     */
    String getPaymentMethod();
    
    /**
     * Validate payment details before processing
     * @param accountInfo account/card information
     * @return true if valid, false otherwise
     */
    boolean validatePaymentDetails(String accountInfo);
}
