package md.utm.tmps.lab2.domain.payment.external;

/**
 * External Stripe API (Adaptee)
 * This represents another third-party payment system with a different interface
 */
public class StripeAPI {
    
    public boolean charge(String cardToken, int amountInCents) {
        // Simulate Stripe payment processing
        System.out.println("  → Stripe: Initializing Stripe SDK...");
        System.out.println("  → Stripe: Charging $" + String.format("%.2f", amountInCents / 100.0) + " to card token: " + cardToken);
        System.out.println("  → Stripe: Transaction approved");
        return true;
    }
    
    public boolean validateToken(String cardToken) {
        // Simple token validation
        return cardToken != null && cardToken.startsWith("tok_") && cardToken.length() > 10;
    }
    
    public String getGatewayName() {
        return "Stripe Payment Platform";
    }
}
