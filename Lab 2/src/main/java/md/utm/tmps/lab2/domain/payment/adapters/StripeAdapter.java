package md.utm.tmps.lab2.domain.payment.adapters;

import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.payment.external.StripeAPI;

/**
 * Adapter for Stripe payment system
 * Adapts StripeAPI to work with our PaymentProcessor interface
 */
public class StripeAdapter implements PaymentProcessor {
    private final StripeAPI stripeAPI;
    
    public StripeAdapter() {
        this.stripeAPI = new StripeAPI();
    }
    
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        // Adapt our interface to Stripe's interface
        // Stripe expects amount in cents, not dollars
        int amountInCents = (int) (amount * 100);
        System.out.println("Stripe Adapter: Converting $" + String.format("%.2f", amount) + 
                          " to " + amountInCents + " cents for " + stripeAPI.getGatewayName());
        return stripeAPI.charge(accountInfo, amountInCents);
    }
    
    @Override
    public String getPaymentMethod() {
        return "Stripe";
    }
    
    @Override
    public boolean validatePaymentDetails(String accountInfo) {
        return stripeAPI.validateToken(accountInfo);
    }
}
