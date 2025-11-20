package md.utm.tmps.lab2.domain.payment.external;

/**
 * External PayPal API (Adaptee)
 * This represents a third-party payment system with its own interface
 */
public class PayPalAPI {
    
    public boolean sendPayment(String email, double amount) {
        // Simulate PayPal payment processing
        System.out.println("  → PayPal: Connecting to PayPal servers...");
        System.out.println("  → PayPal: Sending payment of $" + String.format("%.2f", amount) + " to " + email);
        System.out.println("  → PayPal: Payment authorized by PayPal");
        return true;
    }
    
    public boolean verifyEmail(String email) {
        // Simple email validation
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public String getServiceName() {
        return "PayPal Payment Gateway";
    }
}
